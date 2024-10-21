package com.usmobile.assessment.user_service.request.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // create getter setters
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUsersRequest {
    private List<UpdateUserRequest> userRequests;
}
