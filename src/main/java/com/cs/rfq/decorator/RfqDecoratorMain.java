package com.cs.rfq.decorator;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.HashMap;
import java.util.Map;

public class RfqDecoratorMain {

    public static void main(String[] args) throws Exception {
        String environment = System.getenv("RFQ_DECORATOR_ENVIRONMENT");
        if (environment == null) {
            environment = "dev";
        }

        System.setProperty("hadoop.home.dir", "C:\\Java\\hadoop-2.9.2");
        System.setProperty("spark.master", "local[4]");

        // Create a Spark configuration and set a sensible app name
        SparkConf conf = new SparkConf().setAppName("RfqDecorator");

        // Create a Spark streaming context
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));

        // Create a Spark session
        SparkSession session = SparkSession.builder()
                .appName("RfqDecorator")
                .getOrCreate();

        // Create a new RfqProcessor and set it listening for incoming RFQs
        RfqProcessor rfqProcessor = new RfqProcessor(session, jssc);
        if (environment.equals("dev")) {
            rfqProcessor.start(rfqProcessor::createSocketStream);
        } else {
            String kafkaBootstrapServers = System.getenv("KAFKA_BOOTSTRAP_SERVERS");
            if (kafkaBootstrapServers != null) {
                rfqProcessor.configure("bootstrap.servers", kafkaBootstrapServers);
            }

            rfqProcessor.start(rfqProcessor::createKafkaStream);
        }

        session.stop();
    }
}
