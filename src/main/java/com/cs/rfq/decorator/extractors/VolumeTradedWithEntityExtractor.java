package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public abstract class VolumeTradedWithEntityExtractor implements RfqMetadataExtractor {

    protected String since;

    @Override
    public Map<RfqMetadataFieldNames, Object> extractMetaData(Rfq rfq, SparkSession session, Dataset<Row> trades) {

        String query = String.format("SELECT sum(LastQty) from trade where EntityId='%s' AND SecurityId='%s' AND TradeDate >= '%s'",
                rfq.getEntityId(),
                rfq.getIsin(),
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
