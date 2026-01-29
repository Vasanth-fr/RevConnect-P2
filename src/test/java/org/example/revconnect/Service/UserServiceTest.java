package org.example.revconnect.Service;

import org.example.revconnect.Models.AccountType;
import org.example.revconnect.Models.User;
import org.example.revconnect.Repository.UserRepository;
import org.example.revconnect.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser() {

        when(userRepository.existsByEmail("testmail@gmail.com"))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.register(
                "testmail@gmail.com",
                "test",
                AccountType.PERSONAL
        );

        assertNotNull(user);
        assertEquals("testmail@gmail.com", user.getEmail());
        assertEquals(AccountType.PERSONAL, user.getAccountType());
    }



    @Test
    void testLoginSuccess() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("testmail@gmail.com");
        mockUser.setAccountType(AccountType.PERSONAL);

        when(userRepository.findByEmailAndPassword(
                "testmail@gmail.com",
                "test123"
        )).thenReturn(Optional.of(mockUser));

        User result = userService.login("testmail@gmail.com", "test123");

        assertNotNull(result);
        assertEquals("testmail@gmail.com", result.getEmail());
    }


    @Test
    void testLoginFailure() {
        when(userRepository.findByEmailAndPassword(
                "testmail@gmail.com",
                "wrongpass"
        )).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                userService.login("testmail@gmail.com", "wrongpass")
        );
    }

}

