package com.example.SpringPostgressDocker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class Person {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is required")
  @Pattern(regexp = "^[A-Za-z][A-Za-z\\s]{2,49}$",
           message = "Use 3–50 letters/spaces only")
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Enter a valid email address")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Password is required")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,64}$",
      message = "Min 8 chars incl. letter, number & symbol"
  )
  @Column(nullable = false)
  private String password;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

