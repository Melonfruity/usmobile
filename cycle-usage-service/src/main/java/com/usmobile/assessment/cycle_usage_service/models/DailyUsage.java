package com.usmobile.assessment.cycle_usage_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "daily_usage")
@CompoundIndexes({
        @CompoundIndex(name = "user_mdn_idx", def = "{'userId': 1, 'mdn': 1}") // Index based on userId as first field and mdn as second
})
public class DailyUsage {

    @Id
    private String id;

    // Phone Number Validation Required - What Contraints?
    @NotBlank(message = "MDN is required")
    @Pattern(regexp = "\\d{10}", message = "MDN must be a 10-digit number")
    private String mdn;

    // Foreign Key
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Usage date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat
    private Date usageDate;

    // Not using Integer because there can be fractional values in MB usage such as 2.3 or 1.1
    // Could use BigDecimal but that requires more information on how large or accurate
    // the usage needs to be since BigDecimal has increased computational complexity
    @NotNull(message = "Usage date is required")
    private Double usageInMb;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;
}
