package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class VolumeTradedWithEntityMTDExtractor extends VolumeTradedWithEntityExtractor {

    private String since;

    public VolumeTradedWithEntityMTDExtractor() {
        this.since = DateTime.now().getYear() + '-' + DateTime.now().getMonthOfYear() +"-01";
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedMonthToDate, volume);
        return results;
    }


}
