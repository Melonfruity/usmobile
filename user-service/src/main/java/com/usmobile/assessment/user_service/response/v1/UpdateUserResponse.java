package com.usmobile.assessment.user_service.response.v1;

import com.usmobile.assessment.user_service.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {
    UserDTO user;
}
