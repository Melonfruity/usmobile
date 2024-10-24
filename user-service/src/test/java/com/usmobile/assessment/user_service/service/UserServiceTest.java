package com.usmobile.assessment.user_service.service;

import com.usmobile.assessment.user_service.exception.EmailAlreadyExistsException;
import com.usmobile.assessment.user_service.models.User;
import com.usmobile.assessment.user_service.repository.UserRepository;
import com.usmobile.assessment.user_service.request.v1.user.CreateUserRequest;
import com.usmobile.assessment.user_service.request.v1.UpdateUserRequest;
import com.usmobile.assessment.user_service.response.v1.CreateUserResponse;
import com.usmobile.assessment.user_service.response.v1.UpdateUserResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;
import java.util.List;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.0");

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
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
        User user1 = new User("1", "firstName1", "lastName1", "email1@email.com", "password", date, date);
        User user2 = new User("2", "firstName2", "lastName2", "email2@email.com", "password", date, date);
        return List.of(user1, user2);
    }

    @Nested
    @DisplayName("createUser Tests")
    class createUserTests {
        @Test
        @DisplayName("cannot create user if email is taken")
        void cannotCreateUserIfEmailIsTaken() {
            // Arrange
            CreateUserRequest request = new CreateUserRequest("firstName3", "lastName3", "email1@email.com", "password");

            // Act
            EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(request));

            // Assert
            assertThat(exception.getMessage()).isEqualTo("email1@email.com");
        }

        @Test
        @DisplayName("can create user if email is not taken")
        void canCreateUserIfEmailIsNotTaken() {
            // Arrange
            CreateUserRequest request = new CreateUserRequest("name", "name", "email3@email.com", "password");

            // Act
            CreateUserResponse response = userService.createUser(request);

            // Assert
            assertThat(response.getClass()).isEqualTo(CreateUserResponse.class);
            assertThat(response.getUser().getEmail()).isEqualTo("email3@email.com");
        }
    }

    @Nested
    @DisplayName("updateUser Tests")
    class updateUserTests {
        @Test
        @DisplayName("can update user with missing fields")
        void canUpdateUserWithMissingFields() {
            // Arrange
            UpdateUserRequest request = new UpdateUserRequest("", "newLastName1", null);

            // Act
            UpdateUserResponse response = userService.updateUser(request, "1");

            // Assert
            assertThat(response.getUser().getFirstName()).isEqualTo("firstName1");
            assertThat(response.getUser().getLastName()).isEqualTo("newLastName1");
            assertThat(response.getUser().getEmail()).isEqualTo("email1@email.com");
        }
    }
}