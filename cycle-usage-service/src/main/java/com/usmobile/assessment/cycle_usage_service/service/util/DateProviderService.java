package com.usmobile.assessment.cycle_usage_service.service.util;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateProviderService {
    public Date getCurrentDate() {
        // Set the timezone to EST
        TimeZone estTimeZone = TimeZone.getTimeZone("America/New_York");
        Calendar calendar = Calendar.getInstance(estTimeZone);
        return calendar.getTime();
    }
}