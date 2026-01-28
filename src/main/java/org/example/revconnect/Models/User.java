package org.example.revconnect.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Profile fields
    private String name;
    private String bio;
    private String location;
    private String website;

    // PERSONAL / CREATOR / BUSINESS
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
}
