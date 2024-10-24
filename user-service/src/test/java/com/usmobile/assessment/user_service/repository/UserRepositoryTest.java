package com.usmobile.assessment.user_service.repository;

import com.usmobile.assessment.user_service.models.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRepositoryTest {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.0");

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
    }

    @BeforeEach
    public void setUp() {
        userRepository.saveAll(createMockUser());
    }

    private List<User> createMockUser() {
        Date date = new Date();
        User user1 = new User("1", "user1", "name1", "email1@email.com", "password", date, date);
        User user2 = new User("2", "user2", "name2", "email2@email.com", "password", date, date);
        return List.of(user1, user2);
    }

    @Nested
    @DisplayName("findByEmail")
    class findByEmail {
        @Test
        @DisplayName("if user exists return the user")
        void testReturnExistingUser() {
            // Arrange

            // Act
            Optional<User> user = userRepository.findByEmail("email1@email.com");
            // Assert
            assertThat(user.isPresent()).isTrue();
            assertThat(user.get().getId()).isEqualTo("1");
        }
    }
}