package com.usmobile.assessment.cycle_usage_service.repository;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import com.usmobile.assessment.cycle_usage_service.queries.BillingCycleQueries;
import com.usmobile.assessment.cycle_usage_service.queries.CommonQueries;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface BillingCycleRepository extends MongoRepository<BillingCycle, String> {
    @Query(value = BillingCycleQueries.FIND_BY_MDN_AND_USER_ID)
    List<BillingCycle> findByIdAndMdn(String userId, String mdn);

    @Query(value = BillingCycleQueries.FIND_CURRENT_CYCLE)
    List<BillingCycle> findCurrentBillingCycle(String userId, String mdn, Date currentDate);

    @Query(value = BillingCycleQueries.FIND_BY_MDN_AND_USER_ID, sort = CommonQueries.SORT_START_DATE_ASC)
    List<BillingCycle> findBillingCycleHistory(String userId, String mdn);
}