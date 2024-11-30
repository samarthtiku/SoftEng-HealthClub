package com.gymmanager.model;

import java.time.LocalDate;

public class Membership {
    private String userId;
    private LocalDate expirationDate;
    private String membershipLength;

    public Membership(String userId, LocalDate expirationDate, String membershipLength) {
        this.userId = userId;
        this.expirationDate = expirationDate;
        this.membershipLength = membershipLength;
    }

    // Add the missing getUserID method
    public String getUserID() {
        return userId;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean checkStatus() {
        return !LocalDate.now().isAfter(expirationDate);
    }

    public boolean isExpiringIn30Days() {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        return !expirationDate.isAfter(thirtyDaysFromNow) && checkStatus();
    }

    public void renewMembership(String newLength) {
        this.membershipLength = newLength;
        switch (newLength) {
            case "ONE_YEAR":
                this.expirationDate = LocalDate.now().plusYears(1);
                break;
            case "THREE_YEARS":
                this.expirationDate = LocalDate.now().plusYears(3);
                break;
            case "THREE_MONTHS":
                this.expirationDate = LocalDate.now().plusMonths(3);
                break;
            case "SIX_MONTHS":
                this.expirationDate = LocalDate.now().plusMonths(6);
                break;
        }
    }

    public String getType() {
        return membershipLength;
    }
}