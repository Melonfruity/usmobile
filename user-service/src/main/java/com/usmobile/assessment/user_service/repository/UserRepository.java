package com.usmobile.assessment.user_service.repository;

import com.usmobile.assessment.user_service.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}