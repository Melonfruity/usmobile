package com.usmobile.assessment.user_service.controller;

import com.usmobile.assessment.user_service.dto.request.CreateUserRequest;
import com.usmobile.assessment.user_service.dto.request.UpdateUserRequest;
import com.usmobile.assessment.user_service.dto.request.UpdateUsersRequest;
import com.usmobile.assessment.user_service.dto.response.CreateUserResponse;
import com.usmobile.assessment.user_service.dto.response.GetUserResponse;
import com.usmobile.assessment.user_service.service.UserService;
import com.usmobile.assessment.user_service.util.LoggerUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Create New User
    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        LoggerUtils.logInfo("Creating User with request body: ", request);
        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update Users
    @PutMapping("/users")
    public ResponseEntity<Void> updateUsers(@RequestBody @Valid UpdateUsersRequest request) {
        LoggerUtils.logInfo("Updating Users request body: ", request);
        userService.updateUsers(request);
        return ResponseEntity.noContent().build();
    }

    // Update Single User
    // No need to return anything
    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UpdateUserRequest request) {
        LoggerUtils.logInfo("Updating User request body: ", request);
        userService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    // Get User By Id
    @GetMapping("/user/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable String id) {
        GetUserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // Delete User?
}