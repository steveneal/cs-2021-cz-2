package com.cs.rfq.utils;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaUtils {
    private static Map<String, Object> createCommonConfiguration(String bootstarpServers) {
        Map<String, Object> kafkaConfiguration = new HashMap<>();
        kafkaConfiguration.put("bootstrap.servers", bootstarpServers);

        return kafkaConfiguration;
    }

    public static Map<String, Object> createConsumerConfiguration(String bootstarpServers) {
        Map<String, Object> kafkaConfiguration = createCommonConfiguration(bootstarpServers);

        kafkaConfiguration.put("group.id", "rfq-decorator");
        kafkaConfiguration.put("auto.offset.reset", "latest");
        kafkaConfiguration.put("enable.auto.commit", false);
        kafkaConfiguration.put("key.deserializer", StringDeserializer.class);
        kafkaConfiguration.put("value.deserializer", StringDeserializer.class);
        kafkaConfiguration.put("auto.offset.reset", "latest");
        kafkaConfiguration.put("enable.auto.commit", false);

        return kafkaConfiguration;
    }

    public static Map<String, Object> createProducerConfiguration(String bootstarpServers) {
        Map<String, Object> kafkaConfiguration = createCommonConfiguration(bootstarpServers);

        kafkaConfiguration.put("key.serializer", StringSerializer.class);
        kafkaConfiguration.put("value.serializer", StringSerializer.class);

        return kafkaConfiguration;
    }
}
