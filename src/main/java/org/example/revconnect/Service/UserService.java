package org.example.revconnect.Service;

import org.example.revconnect.Models.AccountType;
import org.example.revconnect.Models.User;
import org.example.revconnect.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // REGISTER user
    public void register(String email, String password, AccountType accountType) {

        if (accountType == null) {
            throw new IllegalArgumentException("Account type is required");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // (hash later, not now)
        user.setAccountType(accountType);

        userRepository.save(user);
    }

    // LOGIN user
    public User login(String email, String password) {

        return userRepository
                .findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalStateException("Invalid credentials"));
    }
}
