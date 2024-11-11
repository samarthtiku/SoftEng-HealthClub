package com.gymmanager.system;

import com.gymmanager.enums.Screen;
import java.util.Scanner;

public class Console {
    private Screen currentScreen;
    private Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.currentScreen = Screen.LOGIN_SCREEN;
    }

    public void showLoginScreen() {
        System.out.println("\n=== Software Engineering Gym Login ===");
        System.out.print("UserID: ");
        String userID = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        // Process login...
    }

    public void showHomeScreen(String userType) {
        System.out.println("\n=== Welcome " + userType + " ===");
        displayMenuOptions(userType);
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
}