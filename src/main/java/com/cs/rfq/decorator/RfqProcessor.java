package com.cs.rfq.decorator;

import com.cs.rfq.decorator.extractors.*;
import com.cs.rfq.decorator.publishers.MetadataJsonLogPublisher;
import com.cs.rfq.decorator.publishers.MetadataPublisher;
import com.google.gson.JsonParseException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

public class RfqProcessor {

    private final static Logger log = LoggerFactory.getLogger(RfqProcessor.class);

    private final MetadataPublisher publisher;

    private final SparkSession session;

    private final JavaStreamingContext streamingContext;

    private final Map<String, Object> configuration = new HashMap<>();

    private Dataset<Row> trades;

    private final List<RfqMetadataExtractor> extractors = new ArrayList<>();

    public RfqProcessor(SparkSession session, JavaStreamingContext streamingContext) {
        this.session = session;
        this.streamingContext = streamingContext;

        // Load the trade data
        trades = new TradeDataLoader().loadTrades(session, getClass().getResource("trades.json").getPath());

        // Register extractors
        extractors.add(new TotalTradesWithEntityExtractor());
        extractors.add(new VolumeTradedWithEntityYTDExtractor());
        extractors.add(new VolumeTradedWithEntityMTDExtractor());
        extractors.add(new VolumeTradedWithEntityWTDExtractor());
        extractors.add(new LiquidityExtractor());

        // Instantiate publisher
        publisher = new MetadataJsonLogPublisher();
    }

    public void configure(String key, Object value) {
        configuration.put(key, value);
    }

    public void start(Supplier<JavaDStream<String>> streamSupplier) throws InterruptedException {
        // Stream data from the input socket on localhost:9000
        JavaDStream<String> elements = streamSupplier.get();

        // Convert each incoming line to a Rfq object and call processRfq method on it
        JavaDStream<Rfq> rfqs = elements.map(x -> {
            try {
                return Rfq.fromJson(x);
            } catch (JsonParseException e) {
                log.warn("Unable to parse json for RFQ: \n" + e.getMessage());
                return null;
            }
        }).filter(x -> x != null);
        rfqs.foreachRDD(rdd -> rdd.collect().forEach(x -> processRfq(x)));

        // Start the streaming context and wait for termination
        streamingContext.start();
        streamingContext.awaitTermination();
    }

    public JavaDStream<String> createSocketStream() {
        return streamingContext.socketTextStream(
                (String) configuration.getOrDefault("host", "localhost"),
                (Integer) configuration.getOrDefault("port", 9000));
    }

    public JavaDStream<String> createKafkaStream() {
        configuration.putIfAbsent("bootstrap.servers", "localhost:9092");
        configuration.putIfAbsent("key.deserializer", StringDeserializer.class);
        configuration.putIfAbsent("value.deserializer", StringDeserializer.class);
        configuration.putIfAbsent("group.id", "rfq");
        configuration.putIfAbsent("auto.offset.reset", "latest");
        configuration.putIfAbsent("enable.auto.commit", false);

        JavaDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.<String, String>Subscribe(Arrays.asList("rfq"), configuration));
        return stream.map(record -> record.value());
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
        publisher.publishMetadata(metadata);
    }
}
