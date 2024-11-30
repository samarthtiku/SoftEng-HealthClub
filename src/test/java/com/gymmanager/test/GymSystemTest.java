package com.gymmanager.test;

import com.gymmanager.model.*;
import com.gymmanager.system.GymSystem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class GymSystemTest {
    private GymSystem system;
    private static final String VALID_USER_ID = "USER123";
    private static final String VALID_PASSWORD = "pass123";
    private static final String VALID_EMAIL = "user@test.com";

    @Before
    public void setUp() {
        system = new GymSystem();
    }

    @Test
    public void testValidMemberAccess() {
        // Arrange
        setupValidMember();

        // Act
        boolean hasAccess = system.checkMembershipStatus(VALID_USER_ID);

        // Assert
        assertTrue("Member should have access with valid membership", hasAccess);

        // Test visit logging
        system.logMemberVisit(VALID_USER_ID);
        MemberAdmission admission = system.getAdmission(VALID_USER_ID);
        assertNotNull("Admission record should be created", admission);
        assertEquals("Visit frequency should be 1", 1,
                admission.getVisitFrequency());
    }

    @Test
    public void testMembershipValidation() {
        // Arrange
        User user = new User(VALID_USER_ID, VALID_PASSWORD, VALID_EMAIL, "member");
        Membership membership = new Membership(VALID_USER_ID,
                LocalDate.now().minusDays(1), "THREE_MONTHS");

        system.addUser(user);
        system.addMembership(membership);

        // Act & Assert
        assertFalse("Expired membership should not grant access",
                system.checkMembershipStatus(VALID_USER_ID));
    }

    @Test
    public void testLoginValidation() {
        // Arrange
        setupValidMember();

        // Act & Assert
        assertTrue("Valid credentials should allow login",
                system.verifyLogin(VALID_USER_ID, VALID_PASSWORD));
        assertFalse("Invalid password should not allow login",
                system.verifyLogin(VALID_USER_ID, "wrongpass"));
    }

    private void setupValidMember() {
        User user = new User(VALID_USER_ID, VALID_PASSWORD, VALID_EMAIL, "member");
        Membership membership = new Membership(VALID_USER_ID,
                LocalDate.now().plusMonths(6), "SIX_MONTHS");
        system.addUser(user);
        system.addMembership(membership);
    }
}