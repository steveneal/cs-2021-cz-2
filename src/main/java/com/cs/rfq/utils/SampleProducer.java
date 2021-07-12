package com.cs.rfq.utils;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.RfqProcessor;
import javassist.tools.rmi.Sample;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

public class SampleProducer {
    private final static Logger log = LoggerFactory.getLogger(SampleProducer.class);


    private final KafkaProducer<String, String> producer;
    private Properties props;

    public SampleProducer() {
        props = loadProperties("kafka.properties");
        producer = new KafkaProducer<String, String>(props);
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
        sp.cleanup();
    }

    private Properties loadProperties(String path) {
        Properties props = new Properties();
        InputStream is;

        try {
            is = getClass().getClassLoader().getResourceAsStream(path);

            if (is != null) {
                props.load(is);
            }
            is.close();

        } catch (IOException e) {
            log.error("Cannot load properties.");
            exit(1);
        }

        return props;
    }

    private void publish(String topic, String key, String value) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (metadata, e) -> {
            if (e != null) {
                log.error("Error publishing message", e);
                return;
            }
        }).get();
    }

    private void cleanup() {
        producer.close();
    }
}
