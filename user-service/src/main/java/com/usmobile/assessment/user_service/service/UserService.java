package com.usmobile.assessment.user_service.service;

import com.usmobile.assessment.user_service.dto.request.CreateUserRequest;
import com.usmobile.assessment.user_service.dto.request.UpdateUserRequest;
import com.usmobile.assessment.user_service.dto.request.UpdateUsersRequest;
import com.usmobile.assessment.user_service.dto.response.CreateUserResponse;
import com.usmobile.assessment.user_service.dto.response.GetUserResponse;
import com.usmobile.assessment.user_service.models.User;
import com.usmobile.assessment.user_service.repository.UserRepository;
import com.usmobile.assessment.user_service.exception.ResourceNotFoundException;
import com.usmobile.assessment.user_service.util.LoggerUtils;
import com.usmobile.assessment.user_service.util.PasswordUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtils passwordUtils;

    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordUtils.hashPassword(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(savedUser);
    }

    public void updateUsers(UpdateUsersRequest requests) {
        requests
            .getUserRequests()
            .forEach(request -> updateUser(request));
    }

    public void updateUser(UpdateUserRequest request) {
        LoggerUtils.logInfo("Fetching User with Id: ", request.getId());
        User existingUser = userRepository.findById(request.getId()).orElseThrow(() ->
                new RuntimeException("User not found")); // Handle user not found

        // Update fields only if they are present
        if (request.getFirstName() != null) {
            existingUser.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            existingUser.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            existingUser.setEmail(request.getEmail());
        }

        LoggerUtils.logInfo("Updating User", existingUser.getId());
        userRepository.save(existingUser);
    }

    public GetUserResponse getUserById(String id) {
        LoggerUtils.logInfo("Get Users with Id: ", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new GetUserResponse(user);
    }
}
