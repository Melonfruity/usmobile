package com.usmobile.assessment.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // create getter setters
@NoArgsConstructor // For Serialization / Deserialization JSON
@AllArgsConstructor // Simplifying Object Creation for Creation
public class UpdateUserRequest {
    private String id;

    private String firstName;

    private String lastName;

    @Email(message = "Email should be valid")
    private String email;
}
