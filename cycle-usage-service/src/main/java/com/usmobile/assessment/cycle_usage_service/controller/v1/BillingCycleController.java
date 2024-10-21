package com.usmobile.assessment.cycle_usage_service.controller.v1;

import com.usmobile.assessment.cycle_usage_service.request.v1.CreateBillingCycleRequest;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleHistoryResponse;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleUsageResponse;
import com.usmobile.assessment.cycle_usage_service.response.v1.CreateBillingCycleResponse;
import com.usmobile.assessment.cycle_usage_service.security.util.BasicUserDetails;
import com.usmobile.assessment.cycle_usage_service.service.BillingCycleService;
import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cycle")
public class BillingCycleController {

    private final BillingCycleService billingCycleService;

    @Autowired
    public BillingCycleController(BillingCycleService billingCycleService) {
        this.billingCycleService = billingCycleService;
    }

    // Get Current Cycle Usage
    @GetMapping("/total-usage")
    public ResponseEntity<BillingCycleUsageResponse> getDailyUsageForCurrentCycle(
            @RequestParam @Valid String mdn,
            @AuthenticationPrincipal BasicUserDetails basicUserDetails) {
        BillingCycleUsageResponse response = billingCycleService.getDailyUsageForCurrentCycle(basicUserDetails.getUserId(), mdn);
        return ResponseEntity.ok(response);
    }

    // Get Cycle History for a given MDN
    @GetMapping("/history")
    public ResponseEntity<BillingCycleHistoryResponse> getCycleHistory(
            @RequestParam @Valid String mdn,
            @AuthenticationPrincipal BasicUserDetails basicUserDetails) {
        LoggerUtil.logInfo("Getting Cycle History");
        BillingCycleHistoryResponse response = billingCycleService.getCycleHistory(basicUserDetails.getUserId(), mdn);
        return ResponseEntity.ok(response);
    }

    // TODO Remove
    // Create Cycle Data for Testing
    @PostMapping
    public ResponseEntity<CreateBillingCycleResponse> createBillingCycles(
            @RequestBody @Valid CreateBillingCycleRequest request) {
        LoggerUtil.logInfo("Creating Billing Cycle Test Data: ", request);
        CreateBillingCycleResponse response = billingCycleService.createBillingCycles(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
