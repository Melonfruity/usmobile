package com.usmobile.assessment.cycle_usage_service.queries;

public class BillingCycleQueries {
    // Find All Cycles That Has Both UserId and MSDN
    public static final String FIND_BY_MDN_AND_USER_ID = "{ 'mdn': ?0, 'userId': ?1 }";
    // Find The Cycle Which Falls Within The Current Date
    public static final String FIND_CURRENT_CYCLE = "{ 'userId': ?0, 'mdn': ?1, 'startDate': { '$lte': ?2 }, 'endDate': { '$gte': ?2 } }";
}
