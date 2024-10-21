package com.usmobile.assessment.cycle_usage_service.service;

import com.usmobile.assessment.cycle_usage_service.request.v1.CreateBillingCycleRequest;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleHistoryResponse;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleUsageResponse;
import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import com.usmobile.assessment.cycle_usage_service.repository.BillingCycleRepository;
import com.usmobile.assessment.cycle_usage_service.repository.DailyUsageRepository;
import com.usmobile.assessment.cycle_usage_service.response.v1.CreateBillingCycleResponse;
import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Service
public class BillingCycleService {

    private final BillingCycleRepository billingCycleRepository;
    private final DailyUsageRepository dailyUsageRepository;

    @Autowired
    public BillingCycleService(BillingCycleRepository cycleRepository, DailyUsageRepository dailyUsageRepository) {
        this.billingCycleRepository = cycleRepository;
        this.dailyUsageRepository = dailyUsageRepository;
    }

    // Get The Daily Usage Within A Cycle Of A Given MDN & UserId
    public BillingCycleUsageResponse getDailyUsageForCurrentCycle(String userId, String mdn) {
        LoggerUtil.logInfo("Getting Daily Usage Within Current Cycle For User And MDN: ", userId, mdn);
        // Set the timezone to EST
        TimeZone estTimeZone = TimeZone.getTimeZone("America/New_York");

        // Get the current date
        Calendar calendar = Calendar.getInstance(estTimeZone);
        Date today = calendar.getTime();

        // Retrieve the current billing cycle using Spring Data's Query Generator
        List<BillingCycle> currentCycles = billingCycleRepository.findCurrentBillingCycle(userId, mdn, today);
        if (currentCycles.size() != 1) {
            LoggerUtil.logError("Current Billing Cycle Is Of Size :" + currentCycles.size());
            return new BillingCycleUsageResponse(List.of());
        }
        // Assumption is that there should only be 1 Cycle Per Phone Number Per Current Cycle
        BillingCycle currentCycle = currentCycles.get(0);

        LoggerUtil.logInfo("Current Billing Cycle: ", currentCycle.getStartDate(), currentCycle.getEndDate());

        List<DailyUsage> dailyUsages = dailyUsageRepository.findUsageWithinCycle(userId, mdn, currentCycle.getStartDate(), currentCycle.getEndDate());
        return new BillingCycleUsageResponse(dailyUsages);
    }

    // Get Cycle History Of A Given MDN & UserId
    public BillingCycleHistoryResponse getCycleHistory(String userId, String mdn) {
        LoggerUtil.logInfo("Getting Billing Cycle History For User And MDN: ", userId, mdn);
        List<BillingCycle> billingCycles = billingCycleRepository.findBillingCycleHistory(userId, mdn);
        return new BillingCycleHistoryResponse(billingCycles);
    }

    // Create Cycle Histories Of An Array Of UserId, MDN And Dates For Testing
    public CreateBillingCycleResponse createBillingCycles(CreateBillingCycleRequest request) {
        List<BillingCycle> billingCycles = request.getBillingCycles().stream().map(dto -> {
           BillingCycle billingCycle = new BillingCycle();
            billingCycle.setUserId(dto.getUserId());
            billingCycle.setMdn(dto.getMdn());
            billingCycle.setStartDate(dto.getStartDate());
            billingCycle.setEndDate(dto.getEndDate());
            return billingCycle;
        }).collect(Collectors.toList());

        List<BillingCycle> savedBillingCycles = billingCycleRepository.saveAll(billingCycles);
        return new CreateBillingCycleResponse(savedBillingCycles);
    }
}
