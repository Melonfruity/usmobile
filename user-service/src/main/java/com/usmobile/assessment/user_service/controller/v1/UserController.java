package com.usmobile.assessment.user_service.controller.v1;

import com.usmobile.assessment.user_service.request.v1.CreateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUserRequest;
import com.usmobile.assessment.user_service.response.v1.CreateUserResponse;
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

    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        LoggerUtil.logInfo("Creating User with request body: ", request);
        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update Single User
    // No need to return anything ?
    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UpdateUserRequest request) {
        LoggerUtil.logInfo("Updating User request body: ", request);
        userService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<String> createJwtToken(@PathVariable String id){
//        String token = jwtUtil.generateToken(id);
//        String userId = jwtUtil.extractUserId(token);
//        return ResponseEntity.ok("Token:" + token + " userId: " + userId);
//    }
}
