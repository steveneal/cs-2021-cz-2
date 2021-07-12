package com.cs.rfq.decorator.stream.creators;

import com.cs.rfq.decorator.Options;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SocketStreamCreator implements StreamCreator {

    private final static Logger log = LoggerFactory.getLogger(SocketStreamCreator.class);

    public JavaDStream<String> create(JavaStreamingContext streamingContext, Map<String, String> configuration) {
        String host = configuration.get(Options.SOCKET_HOST);
        String givenPort = configuration.get(Options.SOCKET_PORT);

        int port;
        try {
            port = Integer.parseInt(givenPort);
        } catch (NumberFormatException e) {
            log.warn("Invalid socket port, defaulting to 9000", e);
            port = 9000;
        }

        return streamingContext.socketTextStream(host, port);
    }
}
