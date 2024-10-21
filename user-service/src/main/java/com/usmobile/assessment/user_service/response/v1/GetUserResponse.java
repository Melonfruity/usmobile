package com.usmobile.assessment.user_service.response.v1;

import com.usmobile.assessment.user_service.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private User user;
}
