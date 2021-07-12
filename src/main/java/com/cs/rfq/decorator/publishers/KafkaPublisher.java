package com.cs.rfq.decorator.publishers;

import com.cs.rfq.decorator.extractors.RfqMetadataFieldNames;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KafkaPublisher implements MetadataPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaPublisher.class);

    private final Gson gson;

    public KafkaPublisher() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void publishMetadata(Map<RfqMetadataFieldNames, Object> metadata) {
        String s = new GsonBuilder().setPrettyPrinting().create().toJson(metadata);
        log.info(String.format("Publishing metadata:%n%s", s));
    }
}
