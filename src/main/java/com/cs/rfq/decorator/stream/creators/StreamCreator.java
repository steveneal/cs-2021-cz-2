package com.cs.rfq.decorator.stream.creators;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.Map;

public interface StreamCreator {
    JavaDStream<String> create(JavaStreamingContext streamingContext, Map<String, String> configuration);
}
