package com.cs.rfq.decorator.publishers;

import com.cs.rfq.decorator.Options;
import com.cs.rfq.decorator.extractors.RfqMetadataFieldNames;
import com.cs.rfq.utils.KafkaUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class KafkaPublisher implements MetadataPublisher {
    private static final Logger log = LoggerFactory.getLogger(KafkaPublisher.class);

    private final Map<String, String> configuration;
    private final KafkaProducer<String, String> producer;
    private final Gson gson;

    public KafkaPublisher(Map<String, String> configuration) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        this.configuration = configuration;
        this.producer = createProducer(configuration);
    }

    @Override
    public void publishMetadata(Map<RfqMetadataFieldNames, Object> metadata) {
        String s = new GsonBuilder().setPrettyPrinting().create().toJson(metadata);

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                configuration.get(Options.KAFKA_METADATA_TOPIC),
                metadata.get(RfqMetadataFieldNames.rfqId).toString(),
                s);

        try {
            producer.send(record, (kafkaMetadata, e) -> {
                if (e != null) {
                    log.error("Error publishing message on kafka topic!", e);
                    return;
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error publishing message on kafka topic!", e);
        }
    }

    private static KafkaProducer<String, String> createProducer(Map<String, String> configuration) {
        Map<String, Object> kafkaConfiguration =
                KafkaUtils.createProducerConfiguration(configuration.get(Options.KAFKA_BOOTSTRAP_SERVERS));
        return new KafkaProducer<>(kafkaConfiguration);
    }

    @Override
    public void stop() {
        producer.close();
    }
}
