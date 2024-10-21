package com.usmobile.assessment.cycle_usage_service.repository;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface BillingCycleRepository extends MongoRepository<BillingCycle, String> {
    @Query(value = "{ 'userId': ?0, 'mdn': ?1, 'startDate': { '$lte': ?2 }, 'endDate': { '$gte': ?2 } }")
    List<BillingCycle> findCurrentBillingCycle(String userId, String mdn, Date currentDate);

    @Query(value = "{ 'userId': ?0, 'mdn': ?1 }", sort = "{ 'startDate': 1 }")
    List<BillingCycle> findBillingCycleHistory(String userId, String mdn);
}