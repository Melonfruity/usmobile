package com.usmobile.assessment.cycle_usage_service.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingCycleDTO {

    @NotBlank(message = "User ID is required")
    private String userId; // Foreign key to the user collection

    @NotBlank(message = "MDN is required")
    private String mdn; // Phone number of the customer

    @NotBlank(message = "Start date is required")
    @JsonFormat
    private Date startDate; // Start date of the billing cycle

    @NotBlank(message = "End date is required")
    @JsonFormat
    private Date endDate; // End date of the billing cycle
}
