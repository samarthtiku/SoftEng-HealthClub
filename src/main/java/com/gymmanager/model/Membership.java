package com.gymmanager.model;

import java.time.LocalDate;

public class Membership {
    private String userID;
    private LocalDate expirationDate;
    private String length; // THREE_MONTHS, SIX_MONTHS, ONE_YEAR, THREE_YEARS

    public Membership(String userID, LocalDate expirationDate, String length) {
        this.userID = userID;
        this.expirationDate = expirationDate;
        this.length = length;
    }

    public boolean checkStatus() {
        return LocalDate.now().isBefore(expirationDate) ||
                LocalDate.now().isEqual(expirationDate);
    }

    public boolean isExpiringIn30Days() {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        return expirationDate.isBefore(thirtyDaysFromNow) &&
                expirationDate.isAfter(LocalDate.now());
    }

    public String getUserID() { return userID; }

    public void renewMembership(String newLength) {
        this.length = newLength;
        switch (newLength) {
            case "THREE_MONTHS":
                this.expirationDate = LocalDate.now().plusMonths(3);
                break;
            case "SIX_MONTHS":
                this.expirationDate = LocalDate.now().plusMonths(6);
                break;
            case "ONE_YEAR":
                this.expirationDate = LocalDate.now().plusYears(1);
                break;
            case "THREE_YEARS":
                this.expirationDate = LocalDate.now().plusYears(3);
                break;
        }
    }
}