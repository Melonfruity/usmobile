package com.usmobile.assessment.user_service.request.v1;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    
    private String firstName;

    private String lastName;

    @Email(message = "Email should be valid")
    private String email;
}
