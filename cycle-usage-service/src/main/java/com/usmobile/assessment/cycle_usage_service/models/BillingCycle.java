package com.usmobile.assessment.cycle_usage_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "billing_cycle")
@CompoundIndexes({
        @CompoundIndex(name = "user_mdn_idx", def = "{'userId': 1, 'mdn': 1}", unique = true)
}) // Compound Index Since we're always searching on userId and mdn, unique ensures that the userid and mdn combination is unique
public class BillingCycle {

    @Id
    private String id;

//    @Indexed // We will always search by MDN
    @NotNull(message = "MDN is required")
    @Pattern(regexp = "\\d{10}", message = "MDN must be a 10-digit number")
    private String mdn;

    // Start Date must be in the format: yyyy-MM-dd'T'HH:mm:ss.SSSZ
    @NotNull(message = "Start date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date startDate;

    // End Date must be in the format: yyyy-MM-dd'T'HH:mm:ss.SSSZ
    @NotNull(message = "End date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date endDate;

//    @Indexed // We will always search by UserId
    @NotNull(message = "User ID is required")
    private String userId;
}
