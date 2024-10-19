package com.usmobile.assessment.cycle_usage_analytics_service.adapter.api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserModel {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
