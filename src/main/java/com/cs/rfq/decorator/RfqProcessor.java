package com.cs.rfq.decorator;

import com.cs.rfq.decorator.extractors.*;
import com.cs.rfq.decorator.publishers.KafkaPublisher;
import com.cs.rfq.decorator.publishers.MetadataJsonLogPublisher;
import com.cs.rfq.decorator.publishers.MetadataPublisher;
import com.cs.rfq.decorator.stream.creators.KafkaStreamCreator;
import com.cs.rfq.decorator.stream.creators.SocketStreamCreator;
import com.cs.rfq.decorator.stream.creators.StreamCreator;
import com.google.gson.JsonParseException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RfqProcessor {

    private final static Logger log = LoggerFactory.getLogger(RfqProcessor.class);

    private final Set<MetadataPublisher> publishers = new HashSet<>();

    private final SparkSession session;

    private final JavaStreamingContext streamingContext;

    private final Map<String, String> configuration;

    private final boolean isKafkaEnabled;

    private Dataset<Row> trades;

    private final List<RfqMetadataExtractor> extractors = new ArrayList<>();

    public RfqProcessor(SparkSession session, JavaStreamingContext streamingContext, Map<String, String> configuration
    ) {
        this.session = session;
        this.streamingContext = streamingContext;
        this.configuration = configuration == null ? new HashMap<>() : configuration;

        // Is  Kafka enabled?
        isKafkaEnabled = Boolean.toString(true).equalsIgnoreCase(this.configuration.get(Options.KAFKA_ENABLED));

        // Load the trade data
        trades = new TradeDataLoader().loadTrades(session, getClass().getResource("trades.json").getPath());

        // Register extractors
        extractors.add(new TotalTradesWithEntityExtractor());
        extractors.add(new VolumeTradedWithEntityYTDExtractor());
        extractors.add(new VolumeTradedWithEntityMTDExtractor());
        extractors.add(new VolumeTradedWithEntityWTDExtractor());
        extractors.add(new LiquidityExtractor());

        // Register publishers
        publishers.add(new MetadataJsonLogPublisher());

        if (isKafkaEnabled) {
            publishers.add(new KafkaPublisher(configuration));
        }
    }

    public void start(StreamCreator streamCreator) throws InterruptedException {
        // Stream data from the input socket on localhost:9000
        JavaDStream<String> elements = streamCreator.create(streamingContext, configuration);

        // Convert each incoming line to a Rfq object and call processRfq method on it
        JavaDStream<Rfq> rfqs = elements.map(x -> {
            try {
                return Rfq.fromJson(x);
            } catch (JsonParseException e) {
                log.warn("Unable to parse json for RFQ", e);
                return null;
            }
        }).filter(x -> x != null);
        rfqs.foreachRDD(rdd -> rdd.collect().forEach(x -> processRfq(x)));

        // Start the streaming context and wait for termination
        streamingContext.start();
        System.out.println("RFQ Decorator is running...");
        streamingContext.awaitTermination();
    }

    public void start() throws InterruptedException {
        if (isKafkaEnabled) {
            start(new KafkaStreamCreator());
        } else {
            start(new SocketStreamCreator());
        }
    }

    public void processRfq(Rfq rfq) {
        log.info(String.format("Received Rfq: %s", rfq.toString()));

        // Create a blank map for the metadata to be collected
        Map<RfqMetadataFieldNames, Object> metadata = new HashMap<>();

        // Get metadata from each of the extractors
        for (RfqMetadataExtractor rfqMetadataExtractor : extractors) {
            metadata.putAll(rfqMetadataExtractor.extractMetaData(rfq, session, trades));
        }
        metadata.put(RfqMetadataFieldNames.rfqId, rfq.getId());

        // Publish the metadata
        for (MetadataPublisher publisher : publishers) {
            publisher.publishMetadata(metadata);
        }
    }

    public void stop() {
        for (MetadataPublisher publisher : publishers) {
            publisher.stop();
        }
    }
}
