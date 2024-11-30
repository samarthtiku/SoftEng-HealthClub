package com.gymmanager.system;

import com.gymmanager.enums.Screen;
import java.util.Scanner;

public class Console {
    private Screen currentScreen;
    private Scanner scanner;
    private GymSystem system;

    public Console(GymSystem system) {
        this.scanner = new Scanner(System.in);
        this.currentScreen = Screen.LOGIN_SCREEN;
        this.system = system;
    }

    public void showLoginScreen() {
        System.out.println("\n=== Software Engineering Gym Login ===");
        System.out.print("UserID: ");
        String userID = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (system.verifyLogin(userID, password)) {
            currentScreen = Screen.HOME_SCREEN;
            showHomeScreen(getUserType(userID));
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    public void showHomeScreen(String userType) {
        System.out.println("\n=== Welcome " + userType + " ===");
        displayMenuOptions(userType);
        handleMenuSelection(userType);
    }

    private void displayMenuOptions(String userType) {
        switch(userType.toLowerCase()) {
            case "staff":
                System.out.println("1. Enter MemberID");
                System.out.println("2. Renew Membership");
                System.out.println("3. Create Membership");
                System.out.println("4. Generate Report");
                System.out.println("5. Logout");
                break;
            case "management":
                System.out.println("1. Enter MemberID");
                System.out.println("2. Renew Membership");
                System.out.println("3. Create Membership");
                System.out.println("4. Generate Report");
                System.out.println("5. Edit User Database");
                System.out.println("6. Logout");
                break;
            case "member":
                System.out.println("1. View Account Details");
                System.out.println("2. Logout");
                break;
        }
    }

    private String getUserType(String userID) {
        // In a real implementation, this would get the type from the User object
        return "member"; // Simplified for demonstration
    }

    private void handleMenuSelection(String userType) {
        System.out.print("\nSelect an option: ");
        String selection = scanner.nextLine();
        // Handle menu selection based on user type
    }
}