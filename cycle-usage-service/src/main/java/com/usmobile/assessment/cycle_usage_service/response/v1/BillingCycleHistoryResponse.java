package com.usmobile.assessment.cycle_usage_service.response.v1;

import com.usmobile.assessment.cycle_usage_service.dto.v1.BillingCycleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BillingCycleHistoryResponse {
    List<BillingCycleDTO> billingCycleList;
}
