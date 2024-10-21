package com.usmobile.assessment.cycle_usage_service.repository;

import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import com.usmobile.assessment.cycle_usage_service.queries.DailyUsageQueries;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface DailyUsageRepository extends MongoRepository<DailyUsage, String> {
    @Query(value = DailyUsageQueries.FIND_DAILY_USAGE_WITHIN_CYCLE)
    List<DailyUsage> findUsageWithinCycle(String userId, String mdn, Date startDate, Date endDate);
}