package com.usmobile.assessment.user_service.request.v1.auth;

import lombok.Data;

@Data
public class SignInRequestBody {
    private String id;
    private String email;
}
