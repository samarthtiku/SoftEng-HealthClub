package com.gymmanager.system;

import com.gymmanager.model.*;
import java.util.*;
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

    public void sendMonthlyExpiryNotices() {
        // In a real system, this would connect to an email service
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
}