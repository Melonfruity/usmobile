package com.usmobile.assessment.cycle_usage_service.repository;

import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Date;

public interface DailyUsageRepository extends MongoRepository<DailyUsage, String> {
    @Query(value = "{ 'userId': ?0, 'mdn': ?1, 'usageDate': { '$gte': ?2, '$lte': ?3 } }")
    List<DailyUsage> findUsageWithinCycle(String userId, String mdn, Date startDate, Date endDate);
}