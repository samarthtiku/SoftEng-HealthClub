package com.gymmanager.system;

import com.gymmanager.model.*;
import com.gymmanager.enums.*;
import java.util.*;

public class GymSystem {
    private Map<String, User> users;
    private Map<String, Membership> memberships;
    private Console console;
    private static final int INACTIVITY_TIMEOUT = 900; // 15 minutes in seconds

    public GymSystem() {
        this.users = new HashMap<>();
        this.memberships = new HashMap<>();
        this.console = new Console();
    }

    public boolean checkMembershipStatus(String userID) {
        Membership membership = memberships.get(userID);
        return membership != null && membership.checkStatus();
    }

    public void logMemberVisit(String userID) {
        MemberAdmission admission = new MemberAdmission(userID);
        admission.logVisit();
        admission.updateFrequency();
    }

    public void sendMonthlyExpiryNotices() {
        AutomaticReport report = new AutomaticReport();
        report.generateMembershipExpiryNotices();
        report.sendNotices();
    }
}