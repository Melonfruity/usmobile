package com.usmobile.assessment.cycle_usage_service.response.v1;

import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BillingCycleUsageResponse {
    private List<DailyUsage> dailyUsages;
}