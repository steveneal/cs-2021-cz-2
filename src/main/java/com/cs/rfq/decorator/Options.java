package com.cs.rfq.decorator;

public interface Options {
    String ENVIRONMENT = "com.cs.rfq.decorator.environment";
    String KAFKA_ENABLED = "com.cs.rfq.decorator.kafka.enabled";
    String KAFKA_BOOTSTRAP_SERVERS = "com.cs.rfq.decorator.kafka.bootstrap.servers";
    String KAFKA_RFQ_TOPIC = "com.cs.rfq.decorator.kafka.rfq.consume.topic";
    String KAFKA_METADATA_TOPIC = "com.cs.rfq.decorator.kafka.metadata.produce.topic";
    String SOCKET_HOST = "com.cs.rfq.decorator.socket.host";
    String SOCKET_PORT = "com.cs.rfq.decorator.socket.port";
}
