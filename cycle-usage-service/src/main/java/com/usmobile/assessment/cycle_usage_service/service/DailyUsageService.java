package com.usmobile.assessment.cycle_usage_service.service;

import com.usmobile.assessment.cycle_usage_service.request.v1.CreateDailyUsageRequest;
import com.usmobile.assessment.cycle_usage_service.response.v1.CreateDailyUsageResponse;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleUsageResponse;
import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import com.usmobile.assessment.cycle_usage_service.repository.DailyUsageRepository;
import com.usmobile.assessment.cycle_usage_service.security.util.BasicUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyUsageService {


    private final DailyUsageRepository dailyUsageRepository;

    @Autowired
    public DailyUsageService(DailyUsageRepository dailyUsageRepository) {
        this.dailyUsageRepository = dailyUsageRepository;
    }

    // Create Daily Usage Data for Testing
    public CreateDailyUsageResponse createDailyUsages(CreateDailyUsageRequest request) {
        List<DailyUsage> dailyUsages = request
            .getDailyUsages().stream().map(dto -> {
                DailyUsage usage = new DailyUsage();
                usage.setMdn(dto.getMdn());
                usage.setUserId(dto.getUserId());
                usage.setUsageDate(dto.getUsageDate());
                usage.setUsageInMb(dto.getUsageInMb());
                return usage;
            }).collect(Collectors.toList());;

        List<DailyUsage> savedDailyUsages = dailyUsageRepository.saveAll(dailyUsages);
        return new CreateDailyUsageResponse(savedDailyUsages);
    }
}
