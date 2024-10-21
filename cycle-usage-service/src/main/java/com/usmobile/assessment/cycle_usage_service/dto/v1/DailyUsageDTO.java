// TODO Remove
package com.usmobile.assessment.cycle_usage_service.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class DailyUsageDTO {

    @NotBlank(message = "MDN (phone number) is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "MDN must be a valid 10-digit phone number")
    private String mdn;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Usage date is required")
    @JsonFormat
    private Date usageDate;

    @NotNull(message = "Usage in MB is required")
    private Double usageInMb;
}