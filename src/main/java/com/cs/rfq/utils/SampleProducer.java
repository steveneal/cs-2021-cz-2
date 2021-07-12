package com.cs.rfq.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class SampleProducer {
    private final static Logger log = LoggerFactory.getLogger(SampleProducer.class);


    private final KafkaProducer<String, String> producer;

    public SampleProducer() {
        producer = new KafkaProducer<String, String>(KafkaUtils.createProducerConfiguration("localhost:9092"));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SampleProducer sp = new SampleProducer();
        String json = "{" +
                "'id': '123ABC', " +
                "'traderId': 3351266293154445953, " +
                "'entityId': 5561279226039690843, " +
                "'instrumentId': 'AT0000383864', " +
                "'qty': 250000, " +
                "'price': 1.58, " +
                "'side': 'B' " +
                "}";
        sp.publish("rfq", "123ABC", json);
        sp.destruct();
    }

    private void publish(String topic, String key, String value) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (metadata, e) -> {
            if (e != null) {
                log.error("Error publishing message");
                return;
            }
        }).get();
    }

    private void destruct() {
        producer.close();
    }
}
