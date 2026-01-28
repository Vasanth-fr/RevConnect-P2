package org.example.revconnect.Repository;

import org.example.revconnect.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check if email already exists (used during registration)
    boolean existsByEmail(String email);

    // Login query
    Optional<User> findByEmailAndPassword(String email, String password);

    // Optional helper
    Optional<User> findByEmail(String email);
}
