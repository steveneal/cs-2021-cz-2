package com.cs.rfq.decorator;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.HashMap;
import java.util.Map;

public class RfqDecoratorMain {

    private static final String ENVIRONMENT_ENV_VAR = "RFQ_DECORATOR_ENVIRONMENT";
    private static final String BOOTSTRAP_SERVERS_ENV_VAR = "KAFKA_BOOTSTRAP_SERVERS";
    private static final String RFQ_TOPIC_ENV_VAR = "KAFKA_RFQ_TOPIC";
    private static final String METADATA_TOPIC_ENV_VAR = "KAFKA_METADATA_TOPIC";
    private static final String SOCKET_HOST_ENV_VAR = "RFQ_DECORATOR_SOCKET_HOST";
    private static final String SOCKET_PORT_ENV_VAR = "RFQ_DECORATOR_SOCKET_HOS";


    public static void main(String[] args) throws Exception {
        Map<String, String> configuration = getConfiguration();

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
        RfqProcessor rfqProcessor = new RfqProcessor(session, jssc, configuration);

        try {
            rfqProcessor.start();
        } finally {
            rfqProcessor.stop();
            session.stop();
        }
    }

    private static Map<String, String> getConfiguration() {
        Map<String, String> configuration = new HashMap<>();

        String environment = getOption(ENVIRONMENT_ENV_VAR, "dev");
        configuration.put(Options.ENVIRONMENT, environment);

        if (environment.equals("dev-kafka") || environment.equals("uat") || environment.equals("prod")) {
            configuration.put(Options.KAFKA_ENABLED, "true");
            configuration.put(
                    Options.KAFKA_BOOTSTRAP_SERVERS,
                    getOption(BOOTSTRAP_SERVERS_ENV_VAR, "localhost:9092"));
            configuration.put(
                    Options.KAFKA_RFQ_TOPIC,
                    getOption(RFQ_TOPIC_ENV_VAR, "rfq"));
            configuration.put(
                    Options.KAFKA_METADATA_TOPIC,
                    getOption(METADATA_TOPIC_ENV_VAR, "rfq-metadata"));
        } else {
            configuration.put(
                    Options.SOCKET_HOST,
                    getOption(SOCKET_HOST_ENV_VAR, "localhost"));
            configuration.put(
                    Options.SOCKET_PORT,
                    getOption(SOCKET_PORT_ENV_VAR, "9000"));
        }

        return configuration;
    }

    private static String getOption(String environmentVariable, String defaultValue) {
        String option = System.getenv(environmentVariable);
        if (option == null) {
            option = defaultValue;
        }
        return option;
    }
}
