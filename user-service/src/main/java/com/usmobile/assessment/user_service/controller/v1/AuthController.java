package com.usmobile.assessment.user_service.controller.v1;

import com.usmobile.assessment.user_service.models.User;
import com.usmobile.assessment.user_service.repository.UserRepository;
import com.usmobile.assessment.user_service.request.v1.auth.SignInRequestBody;
import com.usmobile.assessment.user_service.security.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInRequestBody body){
        if (body.getId() != null && !StringUtils.isBlank(body.getId())) {
            return ResponseEntity.ok(jwtUtil.generateToken(body.getId()));
        }
        Optional<User> user = userRepository.findByEmail(body.getEmail());
        if (user.isEmpty()) throw new RuntimeException("Can't find email");
        return ResponseEntity.ok(jwtUtil.generateToken(user.get().getId()));
    }
}
