package com.usmobile.assessment.user_service.controller.v1;

import com.usmobile.assessment.user_service.request.v1.user.CreateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUserRequest;
import com.usmobile.assessment.user_service.response.v1.CreateUserResponse;
import com.usmobile.assessment.user_service.response.v1.UpdateUserResponse;
import com.usmobile.assessment.user_service.security.util.BasicUserDetails;
import com.usmobile.assessment.user_service.service.UserService;
import com.usmobile.assessment.user_service.util.LoggerUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestBody @Valid CreateUserRequest request) {
        LoggerUtil.logInfo("Creating User with request body: ", request);
        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponse> updateUser(
            @RequestBody @Valid UpdateUserRequest request,
            @AuthenticationPrincipal BasicUserDetails basicUserDetails
    ) {
        LoggerUtil.logInfo("Updating User request body: ", request);
        UpdateUserResponse response = userService.updateUser(request, basicUserDetails.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
