package com.usmobile.assessment.cycle_usage_service.response.v1;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class BillingCycleHistoryResponse {
    List<BillingCycle> billingCycleList;
}
