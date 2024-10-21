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
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "daily_usage")
@CompoundIndexes({
        @CompoundIndex(name = "user_mdn_idx", def = "{'userId': 1, 'mdn': 1}", unique = true)
}) // Compound Index Since we're always searching on userId and mdn, unique ensures that the userid and mdn combination is unique
public class DailyUsage {

    @Id
    private String id;

    // Phone Number Validation Required - What Contraints?
    @NotNull(message = "MDN is required")
    @Pattern(regexp = "\\d{10}", message = "MDN must be a 10-digit number")
    private String mdn;

    // Foreign Key
    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Usage date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date usageDate;

    // Not using Integer because there can be fractional values in MB usage such as 2.3 or 1.1
    // Could use BigDecimal but that requires more information on how large or accurate
    // the usage needs to be since BigDecimal has increased computational complexity
    @NotNull(message = "Usage date is required")
    private Double usageInMb;
}
