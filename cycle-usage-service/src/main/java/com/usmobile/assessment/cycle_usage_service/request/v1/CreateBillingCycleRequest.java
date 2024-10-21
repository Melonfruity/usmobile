package com.usmobile.assessment.cycle_usage_service.request.v1;

import com.usmobile.assessment.cycle_usage_service.dto.v1.BillingCycleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillingCycleRequest {
    private List<BillingCycleDTO> billingCycles;
}

