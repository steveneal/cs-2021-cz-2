package com.cs.rfq.decorator.extractors;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VolumeTradedByLegalEntityMTDExtractor extends VolumeTradedByLegalEntityExtractor {
    private DecimalFormat df = new DecimalFormat("00");

    public VolumeTradedByLegalEntityMTDExtractor() {
        this.until = dateUtil.actualDate();
        this.since = dateUtil.getLastMonthToDate();
        //this.since = dateUtil.getLastMonthToDate();
    }

    public VolumeTradedByLegalEntityMTDExtractor(String until) throws ParseException {
        this.until = until;

        this.since = dateUtil.untilDate(until, 30);
    }

    @Override
    public Map<RfqMetadataFieldNames, Object> setVolumeTraded(Object volume) {
        Map<RfqMetadataFieldNames, Object> results = new HashMap<>();
        results.put(RfqMetadataFieldNames.volumeTradedByLegalEntityMonthToDate, volume);
        return results;
    }


}
