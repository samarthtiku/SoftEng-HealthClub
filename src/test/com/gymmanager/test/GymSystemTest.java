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
        setupValidMember();
        assertTrue("Member should have access with valid membership",
                system.checkMembershipStatus(VALID_USER_ID));

        system.logMemberVisit(VALID_USER_ID);
        MemberAdmission admission = system.getAdmission(VALID_USER_ID);
        assertNotNull("Admission record should be created", admission);
        assertEquals("Visit frequency should be 1", 1,
                admission.getVisitFrequency());
    }

    @Test
    public void testMembershipValidation() {
        User user = new User(VALID_USER_ID, VALID_PASSWORD, VALID_EMAIL, "member");
        Membership membership = new Membership(VALID_USER_ID,
                LocalDate.now().minusDays(1), "THREE_MONTHS");

        system.addUser(user);
        system.addMembership(membership);

        assertFalse("Expired membership should not grant access",
                system.checkMembershipStatus(VALID_USER_ID));
    }

    @Test
    public void testLoginValidation() {
        setupValidMember();
        assertTrue("Valid credentials should allow login",
                system.verifyLogin(VALID_USER_ID, VALID_PASSWORD));
        assertFalse("Invalid password should not allow login",
                system.verifyLogin(VALID_USER_ID, "wrongpass"));
    }

    @Test
    public void testMultipleVisits() {
        setupValidMember();

        // Log multiple visits
        for (int i = 0; i < 3; i++) {
            system.logMemberVisit(VALID_USER_ID);
        }

        MemberAdmission admission = system.getAdmission(VALID_USER_ID);
        assertEquals("Visit frequency should match number of visits", 3,
                admission.getVisitFrequency());
    }

    @Test
    public void testUserTypeVerification() {
        User member = new User("MEM123", "pass123", "mem@test.com", "member");
        User staff = new User("STF123", "pass123", "staff@test.com", "staff");
        User management = new User("MGT123", "pass123", "mgt@test.com", "management");

        system.addUser(member);
        system.addUser(staff);
        system.addUser(management);

        assertEquals("Should correctly identify member type", "member",
                system.getUserType("MEM123"));
        assertEquals("Should correctly identify staff type", "staff",
                system.getUserType("STF123"));
        assertEquals("Should correctly identify management type", "management",
                system.getUserType("MGT123"));
    }

    @Test
    public void testExpiryNotices() {
        User user = new User(VALID_USER_ID, VALID_PASSWORD, VALID_EMAIL, "member");
        Membership membership = new Membership(VALID_USER_ID,
                LocalDate.now().plusDays(15), "THREE_MONTHS");

        system.addUser(user);
        system.addMembership(membership);

        // This will print notices to console - in a real system we'd mock the email service
        system.sendMonthlyExpiryNotices();
    }

    private void setupValidMember() {
        User user = new User(VALID_USER_ID, VALID_PASSWORD, VALID_EMAIL, "member");
        Membership membership = new Membership(VALID_USER_ID,
                LocalDate.now().plusMonths(6), "SIX_MONTHS");
        system.addUser(user);
        system.addMembership(membership);
    }
    @Test
    public void testMembershipRenewalProcess() {
        // Setup initial membership
        User user = new User(VALID_USER_ID, VALID_PASSWORD, VALID_EMAIL, "member");
        Membership initialMembership = new Membership(VALID_USER_ID,
                LocalDate.now().plusMonths(1), "THREE_MONTHS");

        system.addUser(user);
        system.addMembership(initialMembership);

        // Verify initial state
        assertTrue("Initial membership should be active",
                system.checkMembershipStatus(VALID_USER_ID));

        // Get the membership and renew it
        Membership membership = system.getMembership(VALID_USER_ID);
        LocalDate originalExpirationDate = membership.getExpirationDate();
        membership.renewMembership("ONE_YEAR");

        // Verify renewal
        assertTrue("Membership should still be active after renewal",
                system.checkMembershipStatus(VALID_USER_ID));
        assertTrue("New expiration date should be later than original",
                membership.getExpirationDate().isAfter(originalExpirationDate));
        assertEquals("New expiration should be one year from now",
                LocalDate.now().plusYears(1),
                membership.getExpirationDate());

        // Verify the membership is properly stored in the system
        assertNotNull("System should still have the membership record",
                system.getMembership(VALID_USER_ID));
    }
}