package com.usmobile.assessment.cycle_usage_service.service;

import com.usmobile.assessment.cycle_usage_service.dto.v1.BillingCycleDTOMapper;
import com.usmobile.assessment.cycle_usage_service.dto.v1.DailyUsageDTOMapper;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleHistoryResponse;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleUsageResponse;
import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import com.usmobile.assessment.cycle_usage_service.repository.BillingCycleRepository;
import com.usmobile.assessment.cycle_usage_service.repository.DailyUsageRepository;
import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public BillingCycleUsageResponse getDailyUsageForCurrentCycle(String userId, String mdn) throws Exception {
        LoggerUtil.logDebug("Getting Daily Usage Within Current Cycle: ", userId, mdn);

        Date today = new Date();

        List<BillingCycle> currentCycles = billingCycleRepository.findCurrentBillingCycle(userId, mdn, today);

        if (currentCycles.size() > 1) {
            LoggerUtil.logError("Current Billing Cycle Is Of Size :" + currentCycles.size());
            throw new Exception("Data corruption - current cycle size > 1");
        }

        if (currentCycles.isEmpty()) {
            return new BillingCycleUsageResponse(List.of());
        }
        // Assumption is that there should only be 1 Cycle Per Phone Number Per Current Cycle
        BillingCycle currentCycle = currentCycles.get(0);

        LoggerUtil.logDebug("Current Billing Cycle: ", currentCycle.getStartDate(), currentCycle.getEndDate());

        List<DailyUsage> dailyUsages = dailyUsageRepository.findUsageWithinCycle(userId, mdn, currentCycle.getStartDate(), currentCycle.getEndDate());

        return new BillingCycleUsageResponse(dailyUsages
                .stream().map(DailyUsageDTOMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()));
    }

    // Get Cycle History Of A Given MDN & UserId
    public BillingCycleHistoryResponse getCycleHistory(String userId, String mdn) {
        LoggerUtil.logInfo("Getting Billing Cycle History For User And MDN: ", userId, mdn);
        List<BillingCycle> billingCycles = billingCycleRepository.findBillingCycleHistory(userId, mdn);
        return new BillingCycleHistoryResponse(billingCycles
                .stream().map(BillingCycleDTOMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()));
    }
}
