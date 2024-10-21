package com.usmobile.assessment.cycle_usage_service.queries;

public class DailyUsageQueries {
    public static final String FIND_BY_USER_ID_AND_MDN = "{ 'userId': ?0, 'mdn': ?1 }";
    public static final String FIND_DAILY_USAGE_WITHIN_CYCLE = "{ 'userId': ?0, 'mdn': ?1, 'usageDate': { '$gte': ?2, '$lte': ?3 } }";
}
