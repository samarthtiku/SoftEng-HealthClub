package com.gymmanager.model;

public abstract class User {
    private String userID;
    private String password;
    private String email;

    public User(String userID, String password, String email) {
        this.userID = userID;
        this.password = password;
        this.email = email;
    }

    public boolean login() {
        return validateCredentials();
    }

    public void logout() {
        System.out.println("User logged out successfully");
    }

    private boolean validateCredentials() {
        // Here we would check against database
        return true;
    }
}
