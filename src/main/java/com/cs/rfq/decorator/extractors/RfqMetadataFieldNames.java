package com.cs.rfq.decorator.extractors;

/**
 * Enumeration of all metadata that will be published by this component
 */
public enum RfqMetadataFieldNames {
    rfqId,
    tradesWithEntityToday,
    tradesWithEntityPastWeek,
    tradesWithEntityPastYear,
    volumeTradedPastWeek,
    volumeTradedPastMonth,
    volumeTradedPastYear,
    volumeTradedByLegalEntityPastWeek,
    volumeTradedByLegalEntityPastMonth,
    volumeTradedByLegalEntityPastYear,
    averageTradedPriceByLegalEntityPastWeek,
    tradeSideBiasPastWeek,
    tradeSideBiasPastMonth,
    liquidityPastMonth
}
