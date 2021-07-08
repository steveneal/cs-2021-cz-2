package com.cs.rfq.decorator;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.spark.sql.types.DataTypes.*;

public class TradeDataLoader {

    private final static Logger log = LoggerFactory.getLogger(TradeDataLoader.class);

    // Schema for the trade data in the JSON files
    private static final StructType tradeDataSchema = new StructType(new StructField[]{
            new StructField("TraderId", LongType, false, Metadata.empty()),
            new StructField("EntityId", LongType, false, Metadata.empty()),
            new StructField("SecurityID", StringType, false, Metadata.empty()),
            new StructField("LastQty", LongType, false, Metadata.empty()),
            new StructField("LastPx", DoubleType, false, Metadata.empty()),
            new StructField("TradeDate", DateType, false, Metadata.empty()),
            new StructField("Currency", StringType, false, Metadata.empty())
    });

    public Dataset<Row> loadTrades(SparkSession session, String path) {
        // Load the trades dataset
        Dataset<Row> trades = session.read().schema(tradeDataSchema).json(path);

        log.info("Schema used for loading trade data:\n" + tradeDataSchema);
        log.info("Number of records loaded: " + trades.count());

        return trades;
    }
}
