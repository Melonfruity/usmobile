package com.usmobile.assessment.cycle_usage_service.controller.v1;

import com.usmobile.assessment.cycle_usage_service.request.v1.CreateDailyUsageRequest;
import com.usmobile.assessment.cycle_usage_service.response.v1.CreateDailyUsageResponse;
import com.usmobile.assessment.cycle_usage_service.security.util.JwtUtil;
import com.usmobile.assessment.cycle_usage_service.service.DailyUsageService;
import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/daily-usage")
public class DailyUsageController {

    private final DailyUsageService dailyUsageService;

    @Autowired
    public DailyUsageController(DailyUsageService dailyUsageService) {
        this.dailyUsageService = dailyUsageService;
    }

    // Create Daily Usages for Testing
    @PostMapping
    public ResponseEntity<CreateDailyUsageResponse> createDailyUsages(
            @RequestBody @Valid CreateDailyUsageRequest request) {
        LoggerUtil.logInfo("Creating Daily Usage Test Data: ", request);
        CreateDailyUsageResponse response = dailyUsageService.createDailyUsages(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> createJwtToken(@PathVariable String id){
        String token = JwtUtil.generateToken(id);
        String userId = JwtUtil.extractUserId(token);
        return ResponseEntity.ok("Token:" + token + " userId: " + userId);
    }
}
