package com.usmobile.assessment.cycle_usage_service.response.v1;

import com.usmobile.assessment.cycle_usage_service.dto.v1.DailyUsageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BillingCycleUsageResponse {
    private List<DailyUsageDTO> dailyUsages;
}