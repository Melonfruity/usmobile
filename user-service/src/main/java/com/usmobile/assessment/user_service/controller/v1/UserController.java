package com.usmobile.assessment.user_service.controller.v1;

import com.usmobile.assessment.user_service.request.v1.CreateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUsersRequest;
import com.usmobile.assessment.user_service.response.v1.CreateUserResponse;
import com.usmobile.assessment.user_service.response.v1.GetUserResponse;
import com.usmobile.assessment.user_service.response.v1.GetUsersResponse;
import com.usmobile.assessment.user_service.service.UserService;
import com.usmobile.assessment.user_service.util.LoggerUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create New User
    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        LoggerUtil.logInfo("Creating User with request body: ", request);
        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update Users
    @PutMapping("/users")
    public ResponseEntity<Void> updateUsers(@RequestBody @Valid UpdateUsersRequest request) {
        LoggerUtil.logInfo("Updating Users request body: ", request);
        userService.updateUsers(request);
        return ResponseEntity.noContent().build();
    }

    // Update Single User
    // No need to return anything
    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UpdateUserRequest request) {
        LoggerUtil.logInfo("Updating User request body: ", request);
        userService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    // Get User By Id - for Testing Purposes
    @GetMapping("/user/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable String id) {
        GetUserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // Get All Users - for Testing Purposes
    @GetMapping("/users")
    public ResponseEntity<GetUsersResponse> getAllUsers() {
        GetUsersResponse response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }
}
