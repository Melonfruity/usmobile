package com.usmobile.assessment.user_service.response.v1;

import com.usmobile.assessment.user_service.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public CreateUserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}

