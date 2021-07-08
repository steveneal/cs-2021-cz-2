package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityYTDExtractor extends VolumeTradedWithEntityExtractor {

    private String since;

    public VolumeTradedWithEntityYTDExtractor() {
        this.since = DateTime.now().getYear() + "-01-01";
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedYearToDate, volume);
        return results;
    }

}
