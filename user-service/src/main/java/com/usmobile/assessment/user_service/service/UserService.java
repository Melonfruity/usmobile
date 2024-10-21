package com.usmobile.assessment.user_service.service;

import com.usmobile.assessment.user_service.request.v1.CreateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUsersRequest;
import com.usmobile.assessment.user_service.response.v1.CreateUserResponse;
import com.usmobile.assessment.user_service.response.v1.GetUserResponse;
import com.usmobile.assessment.user_service.models.User;
import com.usmobile.assessment.user_service.repository.UserRepository;
import com.usmobile.assessment.user_service.exception.ResourceNotFoundException;
import com.usmobile.assessment.user_service.response.v1.GetUsersResponse;
import com.usmobile.assessment.user_service.util.LoggerUtil;
import com.usmobile.assessment.user_service.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final private UserRepository userRepository;
    final private PasswordUtil passwordUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordUtil.hashPassword(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(savedUser);
    }

    public void updateUsers(UpdateUsersRequest requests) {
        requests
            .getUserRequests()
            .forEach(request -> updateUser(request));
    }

    public void updateUser(UpdateUserRequest request) {
        LoggerUtil.logInfo("Fetching User with Id: ", request.getId());
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

        LoggerUtil.logInfo("Updating User", existingUser.getId());
        userRepository.save(existingUser);
    }

    public GetUserResponse getUserById(String id) {
        LoggerUtil.logInfo("Get Users with Id: ", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new GetUserResponse(user);
    }

    public GetUsersResponse getAllUsers() {
        LoggerUtil.logInfo("Getting All Users");
        List<User> users = userRepository.findAll();
        return new GetUsersResponse(users);
    }
}
