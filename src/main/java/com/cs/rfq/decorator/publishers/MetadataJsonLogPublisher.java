package com.cs.rfq.decorator.publishers;

import com.cs.rfq.decorator.extractors.RfqMetadataFieldNames;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MetadataJsonLogPublisher implements MetadataPublisher {

    private static final Logger log = LoggerFactory.getLogger(MetadataJsonLogPublisher.class);

    private final Gson gson;

    public MetadataJsonLogPublisher() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void publishMetadata(Map<RfqMetadataFieldNames, Object> metadata) {
        String s = new GsonBuilder().setPrettyPrinting().create().toJson(metadata);
        System.out.println(s);
    }
}
