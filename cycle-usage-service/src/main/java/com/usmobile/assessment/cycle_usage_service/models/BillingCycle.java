package com.usmobile.assessment.cycle_usage_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "billing_cycle")
public class BillingCycle {

    @Id
    private String id;

//    @Indexed // We will always search by mdn
    @NotNull(message = "MDN is required")
    @Pattern(regexp = "\\d{10}", message = "MDN must be a 10-digit number")
    private String mdn;

    // Start Date must be in the format: yyyy-MM-dd'T'HH:mm:ss.SSSZ
    @NotNull(message = "Start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat
    private Date startDate;

    // End Date must be in the format: yyyy-MM-dd'T'HH:mm:ss.SSSZ
    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat
    private Date endDate;

    @Indexed // We will always search by UserId
    @NotNull(message = "User ID is required")
    private String userId;
}
