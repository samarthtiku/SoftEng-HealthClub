package com.gymmanager.system;

import com.gymmanager.enums.Screen;
import com.gymmanager.model.User;
import com.gymmanager.model.Membership;
import com.gymmanager.model.MemberAdmission;
import java.util.Scanner;
import java.time.LocalDate;

public class Console {
    private Screen currentScreen;
    private Scanner scanner;
    private GymSystem system;
    private String currentUserID;

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
            currentUserID = userID;
            currentScreen = Screen.HOME_SCREEN;
            showHomeScreen(system.getUserType(userID));
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

    private void handleMenuSelection(String userType) {
        System.out.print("\nSelect an option: ");
        String selection = scanner.nextLine();

        switch(userType.toLowerCase()) {
            case "member":
                handleMemberSelection(selection);
                break;
            case "staff":
                handleStaffSelection(selection);
                break;
            case "management":
                handleManagementSelection(selection);
                break;
        }
    }

    private void handleMemberSelection(String selection) {
        switch(selection) {
            case "1":
                // View Account Details
                displayMemberDetails(currentUserID);
                showHomeScreen("member");
                break;
            case "2":
                System.out.println("\nLogging out...");
                currentScreen = Screen.LOGIN_SCREEN;
                showLoginScreen();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                showHomeScreen("member");
                break;
        }
    }

    private void handleStaffSelection(String selection) {
        switch(selection) {
            case "1":
                // Enter MemberID
                System.out.println("\n=== Enter Member ID ===");
                System.out.print("Enter Member ID: ");
                String memberId = scanner.nextLine();
                if (system.checkMembershipStatus(memberId)) {
                    system.logMemberVisit(memberId);
                    System.out.println("Member visit logged successfully.");
                } else {
                    System.out.println("Invalid membership or expired.");
                }
                displayMemberDetails(memberId);
                showHomeScreen("staff");
                break;
            case "2":
                handleRenewMembership();
                showHomeScreen("staff");
                break;
            case "3":
                handleCreateMembership();
                showHomeScreen("staff");
                break;
            case "4":
                generateReport();
                showHomeScreen("staff");
                break;
            case "5":
                System.out.println("\nLogging out...");
                currentScreen = Screen.LOGIN_SCREEN;
                showLoginScreen();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                showHomeScreen("staff");
                break;
        }
    }

    private void handleManagementSelection(String selection) {
        switch(selection) {
            case "1":
            case "2":
            case "3":
            case "4":
                handleStaffSelection(selection);
                break;
            case "5":
                handleEditUserDatabase();
                showHomeScreen("management");
                break;
            case "6":
                System.out.println("\nLogging out...");
                currentScreen = Screen.LOGIN_SCREEN;
                showLoginScreen();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                showHomeScreen("management");
                break;
        }
    }

    // Renew membership (note: current implementation only changes membership)
    private void handleRenewMembership() {
        System.out.println("\n=== Renew Membership ===");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();

        System.out.println("Choose membership length:");
        System.out.println("1. THREE_MONTHS");
        System.out.println("2. SIX_MONTHS");
        System.out.println("3. ONE_YEAR");
        System.out.println("4. THREE_YEARS");

        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        String length = "";
        switch(choice) {
            case "1": length = "THREE_MONTHS"; break;
            case "2": length = "SIX_MONTHS"; break;
            case "3": length = "ONE_YEAR"; break;
            case "4": length = "THREE_YEARS"; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        Membership membership = system.getMembership(memberId);
        if (membership != null) {
            membership.renewMembership(length);
            System.out.println("Membership renewed successfully.");
        } else {
            System.out.println("Member not found.");
        }
    }

    // Note: this method isn't functional since there is no backend
    // Users need to be added manually in the code
    private void handleCreateMembership() {
        System.out.println("\n=== Create New Membership ===");
        System.out.print("Enter new Member ID: ");
        String memberId = scanner.nextLine();

        System.out.println("Choose membership length:");
        System.out.println("1. THREE_MONTHS");
        System.out.println("2. SIX_MONTHS");
        System.out.println("3. ONE_YEAR");
        System.out.println("4. THREE_YEARS");

        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        String length = "";
        switch(choice) {
            case "1": length = "THREE_MONTHS"; break;
            case "2": length = "SIX_MONTHS"; break;
            case "3": length = "ONE_YEAR"; break;
            case "4": length = "THREE_YEARS"; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        Membership membership = new Membership(memberId, LocalDate.now(), length);
        system.addMembership(membership);
        System.out.println("Membership created successfully.");
    }

    private void generateReport() {
        System.out.println("\n=== Generate Report ===");
        System.out.println("1. Membership Expiry Report");
        System.out.println("2. Member Visit Report");
        System.out.println("3. List of Current Users");

        System.out.print("Choose report type: ");
        String choice = scanner.nextLine();

        switch(choice) {
            case "1":
                system.sendMonthlyExpiryNotices();
                break;
            case "2":
                System.out.print("Enter UserID for visit report: ");
                String userID = scanner.nextLine();
                displayMemberDetails(userID);
                break;
            case "3":
                listCurrentUsers();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void listCurrentUsers() {
        System.out.println("\n=== List of Current Users ===");
        for (User user : system.getUsers().values()) {
            System.out.println("User ID: " + user.getUserID());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Type: " + user.getType());

            Membership membership = system.getMembership(user.getUserID());
            if (membership != null) {
                System.out.println("Membership Status: " + (membership.checkStatus() ? "Active" : "Expired"));
                System.out.println("Membership Type: " + membership.getType());
                System.out.println("Expiration Date: " + membership.getExpirationDate());
            }

            MemberAdmission admission = system.getAdmission(user.getUserID());
            if (admission != null) {
                System.out.println("Last Visit: " + admission.getLastVisit());
                System.out.println("Visit Frequency: " + admission.getVisitFrequency());
            }
            System.out.println();
        }
    }

    // Note: this method isn't functional since there is no backend
    private void handleEditUserDatabase() {
        System.out.println("\n=== Edit User Database ===");
        System.out.println("1. Add User");
        System.out.println("2. Remove User");

        System.out.print("Choose option: ");
        String choice = scanner.nextLine();

        switch(choice) {
            case "1":
                System.out.print("Enter User ID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                System.out.print("Enter Type (member/staff): ");
                String type = scanner.nextLine();

                try {
                    User newUser = new User(userId, password, email, type);
                    system.addUser(newUser);
                    System.out.println("User added successfully.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case "2":
                System.out.println("Remove user functionality coming soon.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void displayMemberDetails(String memberId) {
        System.out.println("\n=== Member Details ===");
        User user = system.getUsers().get(memberId);
        if (user != null) {
            System.out.println("User ID: " + user.getUserID());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Type: " + user.getType());

            Membership membership = system.getMembership(user.getUserID());
            if (membership != null) {
                System.out.println("Membership Status: " + (membership.checkStatus() ? "Active" : "Expired"));
                System.out.println("Membership Type: " + membership.getType());
                System.out.println("Expiration Date: " + membership.getExpirationDate());
            } else {
                System.out.println("Membership Status: No membership found.");
            }

            MemberAdmission admission = system.getAdmission(user.getUserID());
            if (admission != null) {
                System.out.println("Last Visit: " + admission.getLastVisit());
                System.out.println("Visit Frequency: " + admission.getVisitFrequency());
            } else {
                System.out.println("No admission records found.");
            }
        } else {
            System.out.println("Member not found.");
        }
    }
}