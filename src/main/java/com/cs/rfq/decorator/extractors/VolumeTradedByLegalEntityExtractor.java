package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.text.DecimalFormat;
import java.util.Map;

public abstract class VolumeTradedByLegalEntityExtractor implements RfqMetadataExtractor {

    protected DecimalFormat df = new DecimalFormat("00");
    protected String since;

    @Override
    public Map<RfqMetadataFieldNames, Object> extractMetaData(Rfq rfq, SparkSession session, Dataset<Row> trades) {

        String query = String.format("SELECT sum(LastQty) from trade where EntityId='%s' AND TradeDate >= '%s'",
                rfq.getEntityId(),
                since);

        trades.createOrReplaceTempView("trade");
        Dataset<Row> sqlQueryResults = session.sql(query);

        Object volume = sqlQueryResults.first().get(0);
        if (volume == null) {
            volume = 0L;
        }

        return setVolumeTraded(volume);
    }

    protected void setSince(String since) {
        this.since = since;
    }

    protected abstract Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume);
}