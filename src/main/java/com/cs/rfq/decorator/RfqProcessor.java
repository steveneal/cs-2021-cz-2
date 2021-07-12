package com.cs.rfq.decorator;

import com.cs.rfq.decorator.extractors.*;
import com.cs.rfq.decorator.publishers.MetadataJsonLogPublisher;
import com.cs.rfq.decorator.publishers.MetadataPublisher;
import com.google.gson.JsonParseException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RfqProcessor {

    private final static Logger log = LoggerFactory.getLogger(RfqProcessor.class);

    private final SparkSession session;

    private final JavaStreamingContext streamingContext;

    private Dataset<Row> trades;

    private final List<RfqMetadataExtractor> extractors = new ArrayList<>();

    private final MetadataPublisher publisher = new MetadataJsonLogPublisher();

    public RfqProcessor(SparkSession session, JavaStreamingContext streamingContext) {
        this.session = session;
        this.streamingContext = streamingContext;

        // Load the trade data
        trades = new TradeDataLoader().loadTrades(session, getClass().getResource("trades.json").getPath());

        extractors.add(new TotalTradesWithEntityExtractor());
        extractors.add(new VolumeTradedWithEntityPYExtractor());
        extractors.add(new VolumeTradedWithEntityPMExtractor());
        extractors.add(new VolumeTradedWithEntityPWExtractor());
        extractors.add(new VolumeTradedByLegalEntityPYExtractor());
        extractors.add(new VolumeTradedByLegalEntityPMExtractor());
        extractors.add(new VolumeTradedByLegalEntityPWExtractor());
        extractors.add(new AverageTradedPriceByLegalEntityPWExtractor());
        extractors.add(new TradeSideBiasPMExtractor());
        extractors.add(new TradeSideBiasPWExtractor());
    }

    public void startSocketListener() throws InterruptedException {
        // Stream data from the input socket on localhost:9000
        JavaDStream<String> lines = streamingContext.socketTextStream("localhost", 9000);

        // Convert each incoming line to a Rfq object and call processRfq method on it
        JavaDStream<Rfq> rfqs = lines.map(x -> {
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

    public void processRfq(Rfq rfq) {
        log.info(String.format("Received Rfq: %s", rfq.toString()));

        // Create a blank map for the metadata to be collected
        Map<RfqMetadataFieldNames, Object> metadata = new HashMap<>();

        //TODO: get metadata from each of the extractors
        // Features to implement
        for (RfqMetadataExtractor rfqMetadataExtractor : extractors) {
            metadata.putAll(rfqMetadataExtractor.extractMetaData(rfq, session, trades));
        }
        //TODO: publish the metadata
        // Dummy
        log.info(metadata.toString());

    }
}
