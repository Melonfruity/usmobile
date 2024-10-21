package com.usmobile.assessment.cycle_usage_service.response.v1;

import lombok.Getter;
import lombok.Setter;

// Error Response Class
@Setter
@Getter
public class ErrorResponse {
    // Getters and Setters
    private String error;
    private String message;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

}