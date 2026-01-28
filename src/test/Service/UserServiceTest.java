package Service;

import Models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService = new UserService();

    @Test
    void register_success() {

        String email = "test_" + System.currentTimeMillis() + "@mail.com";

        boolean result = userService.register(
                email,
                "1234",
                "PERSONAL"
        );

        assertTrue(result);
    }


    @Test
    void login_success() {

        // Arrange (ensure user exists)
        userService.register(
                "login_test@mail.com",
                "1234",
                "PERSONAL"
        );

        // Act
        User user = userService.login(
                "login_test@mail.com",
                "1234"
        );

        // Assert
        assertNotNull(user);
    }


    @Test
    void login_failure() {
        User user = userService.login("wrong@mail.com", "wrong");
        assertNull(user);
    }
}
