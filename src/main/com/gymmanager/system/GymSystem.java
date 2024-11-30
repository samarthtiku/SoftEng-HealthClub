package com.gymmanager.system;

import com.gymmanager.model.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GymSystem {
    private Map<String, User> users;
    private Map<String, Membership> memberships;
    private Map<String, MemberAdmission> admissions;
    private static final int INACTIVITY_TIMEOUT = 900; // 15 minutes in seconds

    public GymSystem() {
        this.users = new HashMap<>();
        this.memberships = new HashMap<>();
        this.admissions = new HashMap<>();

        // Add some sample data for testing
        setupSampleData();

        // Add test user, membership, and admission data manually
        addTestUserData();
    }

    private void setupSampleData() {
        // Create a sample staff member
        User staffMember = new User("STAFF123", "staff123", "staff@gym.com", "staff");
        addUser(staffMember);

        // Create a sample management user
        User manager = new User("MGMT123", "mgmt123", "manager@gym.com", "management");
        addUser(manager);

        // Create a sample member
        User member = new User("USER123", "pass123", "user@gym.com", "member");
        addUser(member);

        // Create a sample membership
        Membership membership = new Membership("USER123",
                LocalDate.now().plusMonths(6), "SIX_MONTHS");
        addMembership(membership);

    }

    // test user member #2
    private void addTestUserData() {
        // Create a test user
        User testUser = new User("TEST123", "test123", "test@gym.com", "member");
        addUser(testUser);

        // Create a test membership
        Membership testMembership = new Membership("TEST123",
                LocalDate.now().plusMonths(1), "ONE_MONTH");
        addMembership(testMembership);

    }

    public boolean checkMembershipStatus(String userID) {
        Membership membership = memberships.get(userID);
        return membership != null && membership.checkStatus();
    }

    public void logMemberVisit(String userID) {
        MemberAdmission admission = admissions.computeIfAbsent(userID,
                MemberAdmission::new);
        admission.logVisit();
        admission.updateFrequency();
    }

    public MemberAdmission getAdmission(String userID) {
        return admissions.get(userID);
    }

    public boolean verifyLogin(String userID, String password) {
        User user = users.get(userID);
        return user != null && user.login(password);
    }

    public void addUser(User user) {
        users.put(user.getUserID(), user);
    }

    public void addMembership(Membership membership) {
        memberships.put(membership.getUserID(), membership);
    }

    public Membership getMembership(String userID) {
        return memberships.get(userID);
    }

    public String getUserType(String userID) {
        User user = users.get(userID);
        return user != null ? user.getType() : null;
    }

    public void sendMonthlyExpiryNotices() {
        memberships.values().stream()
                .filter(Membership::isExpiringIn30Days)
                .forEach(membership -> {
                    String userID = membership.getUserID();
                    User user = users.get(userID);
                    if (user != null) {
                        System.out.println("Sending notice to: " + user.getEmail());
                    }
                });
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        GymSystem system = new GymSystem();
        Console console = new Console(system);

        System.out.println("\nWelcome to the Gym Management System!");
        System.out.println("Sample login credentials:");
        System.out.println("Staff - UserID: STAFF123, Password: staff123");
        System.out.println("Member - UserID: USER123, Password: pass123");
        System.out.println("Member - UserID: TEST123, Password: test123");
        System.out.println("Management - UserID: MGMT123, Password: mgmt123\n");

        console.showLoginScreen();
    }
}