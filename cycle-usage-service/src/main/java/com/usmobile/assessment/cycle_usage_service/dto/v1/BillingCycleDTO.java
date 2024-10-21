package com.usmobile.assessment.cycle_usage_service.dto.v1;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDate; // Start date of the billing cycle

    // @DateTimeFormat for HTTP Method DTOs
    // @JsonFormat for Serializing / Deserializing Payloads
    @NotBlank(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endDate; // End date of the billing cycle

    // TODO Field isActive for in case this Cycle has been invalidated by any mean such as when a customer has changed their line
}
