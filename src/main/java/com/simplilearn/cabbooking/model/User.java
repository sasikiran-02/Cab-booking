package com.simplilearn.cabbooking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long UserId;

    @Column(name = "Name")  // Retaining original SQL column name
    private String Name;

    @Column(name = "Email")  // Retaining original SQL column name
    private String Email;

    @Column(name = "PhoneNumber")  // Retaining original SQL column name
    private String PhoneNumber;

    @Column(name = "Password")  // Retaining original SQL column name
    private String Password;

    @Column(name = "Username")  // Retaining original SQL column name
    private String username;

    @Column(name = "CreatedAt")  // Retaining original SQL column name
    private LocalDateTime createdAt;

    // Constructor with parameters
    public User(Long UserId, String Name, String Email, String PhoneNumber, String Password, String username, LocalDateTime createdAt) {
        this.UserId = UserId;
        this.Name = Name;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.Password = Password;
        this.username = username;
        this.createdAt = createdAt;
    }

    // Default constructor
    public User() {}

    // Getters and Setters

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long UserId) {
        this.UserId = UserId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
