package com.cs.rfq.decorator.stream.creators;


import com.cs.rfq.decorator.Options;
import com.cs.rfq.utils.KafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KafkaStreamCreator implements StreamCreator {
    @Override
    public JavaDStream<String> create(JavaStreamingContext streamingContext, Map<String, String> configuration) {
        Map<String, Object> kafkaConfiguration = new HashMap<>();
        kafkaConfiguration.put("bootstrap.servers", configuration.get(Options.KAFKA_BOOTSTRAP_SERVERS));
        kafkaConfiguration.put("key.deserializer", StringDeserializer.class);
        kafkaConfiguration.put("value.deserializer", StringDeserializer.class);
        kafkaConfiguration.put("group.id", "rfq");
        kafkaConfiguration.put("auto.offset.reset", "latest");
        kafkaConfiguration.put("enable.auto.commit", false);

        JavaDStream<ConsumerRecord<String, String>> stream = org.apache.spark.streaming.kafka010.KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(
                        Arrays.asList(configuration.get(Options.KAFKA_RFQ_TOPIC)),
                        KafkaUtils.createConsumerConfiguration(configuration.get(Options.KAFKA_BOOTSTRAP_SERVERS))));

        return stream.map(record -> record.value());
    }

}

