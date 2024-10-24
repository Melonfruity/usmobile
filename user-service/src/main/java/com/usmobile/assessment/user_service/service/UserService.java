package com.usmobile.assessment.user_service.service;

import com.usmobile.assessment.user_service.dto.UserDTOMapper;
import com.usmobile.assessment.user_service.exception.EmailAlreadyExistsException;
import com.usmobile.assessment.user_service.request.v1.user.CreateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUserRequest;
import com.usmobile.assessment.user_service.response.v1.CreateUserResponse;
import com.usmobile.assessment.user_service.models.User;
import com.usmobile.assessment.user_service.repository.UserRepository;
import com.usmobile.assessment.user_service.response.v1.UpdateUserResponse;
import com.usmobile.assessment.user_service.util.LoggerUtil;
import com.usmobile.assessment.user_service.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        // Check if user exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()) throw new EmailAlreadyExistsException(request.getEmail());

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordUtil.hashPassword(request.getPassword()));

        LoggerUtil.logDebug("Creating new user with email: ", request.getEmail());

        User savedUser = userRepository.save(user);

        LoggerUtil.logDebug("User successfully saved");

        return new CreateUserResponse(UserDTOMapper.INSTANCE.toDTO(savedUser));

    }

    public UpdateUserResponse updateUser(UpdateUserRequest request, String id) {
        LoggerUtil.logInfo("Fetching User with Id: ", id);

        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")); // Handle user not found

        // Update fields only if they are present
        if (request.getFirstName() != null && !StringUtils.isBlank(request.getFirstName())) {
            existingUser.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !StringUtils.isBlank(request.getLastName())) {
            existingUser.setLastName(request.getLastName());
        }
        if (request.getEmail() != null && !StringUtils.isBlank(request.getEmail())) {
            existingUser.setEmail(request.getEmail());
        }

        LoggerUtil.logInfo("Updating User", existingUser.getId());
        User updateduser = userRepository.save(existingUser);

        return new UpdateUserResponse(UserDTOMapper.INSTANCE.toDTO(updateduser));
    }
}
