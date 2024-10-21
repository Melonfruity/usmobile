package com.usmobile.assessment.user_service.request.v1;

import jakarta.validation.constraints.Email;
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
