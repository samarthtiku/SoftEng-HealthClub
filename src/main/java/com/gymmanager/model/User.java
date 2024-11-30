package com.gymmanager.model;

public class User {
    private String userID;
    private String password;
    private String email;
    private String type;
    private boolean isLoggedIn;

    public User(String userID, String password, String email, String type) {
        validateUserID(userID);
        validatePassword(password);
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.type = type;
        this.isLoggedIn = false;
    }

    private void validateUserID(String userID) {
        if (userID == null || userID.length() < 6 || userID.length() > 10 ||
                !userID.matches(".*\\d+.*")) {
            throw new IllegalArgumentException(
                    "UserID must be 6-10 characters and contain at least one number");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6 || password.length() > 10 ||
                !password.matches(".*\\d+.*")) {
            throw new IllegalArgumentException(
                    "Password must be 6-10 characters and contain at least one number");
        }
    }

    public boolean login(String password) {
        if (this.password.equals(password)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public String getUserID() { return userID; }
    public String getEmail() { return email; }
    public String getType() { return type; }
}