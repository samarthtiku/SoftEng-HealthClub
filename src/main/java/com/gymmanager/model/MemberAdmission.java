package com.gymmanager.model;

import java.time.LocalDateTime;

public class MemberAdmission {
    private String userID;
    private LocalDateTime lastVisit;
    private int visitFrequency;

    public MemberAdmission(String userID) {
        this.userID = userID;
        this.lastVisit = LocalDateTime.now();
        this.visitFrequency = 0;
    }

    public void logVisit() {
        this.lastVisit = LocalDateTime.now();
    }

    public void updateFrequency() {
        visitFrequency++;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public int getVisitFrequency() {
        return visitFrequency;
    }
}