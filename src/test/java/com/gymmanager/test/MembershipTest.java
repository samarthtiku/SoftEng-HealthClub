package com.gymmanager.test;

import com.gymmanager.model.Membership;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

/**
 * Test suite for the Membership class that verifies all core membership functionality
 * including creation, status checking, expiration validation, and renewal operations.
 */
public class MembershipTest {
    private Membership membership;
    private static final String TEST_USER_ID = "USER123";

    /**
     * Set up a fresh membership instance before each test to ensure
     * test isolation and prevent any state bleeding between tests.
     */
    @Before
    public void setUp() {
        // Create a membership that expires in 6 months
        membership = new Membership(
                TEST_USER_ID,
                LocalDate.now().plusMonths(6),
                "SIX_MONTHS"
        );
    }

    /**
     * Verify that a newly created membership with a future expiration date
     * is correctly identified as active.
     */
    @Test
    public void testActiveMembershipStatus() {
        // A membership that expires in the future should be active
        assertTrue("New membership should be active", membership.checkStatus());
    }

    /**
     * Verify that an expired membership is correctly identified as inactive.
     */
    @Test
    public void testExpiredMembershipStatus() {
        // Create a membership that expired yesterday
        Membership expiredMembership = new Membership(
                TEST_USER_ID,
                LocalDate.now().minusDays(1),
                "THREE_MONTHS"
        );

        assertFalse("Expired membership should be inactive",
                expiredMembership.checkStatus());
    }

    /**
     * Verify that a membership expiring exactly today is still considered active.
     * This test ensures we handle edge cases correctly.
     */
    @Test
    public void testSameDayExpirationStatus() {
        Membership expiringToday = new Membership(
                TEST_USER_ID,
                LocalDate.now(),
                "THREE_MONTHS"
        );

        assertTrue("Membership expiring today should still be active",
                expiringToday.checkStatus());
    }

    /**
     * Test the 30-day expiration warning detection.
     * This functionality is crucial for sending timely renewal notifications.
     */
    @Test
    public void testExpiringIn30DaysDetection() {
        // Test membership expiring in 25 days
        Membership expiringMembership = new Membership(
                TEST_USER_ID,
                LocalDate.now().plusDays(25),
                "THREE_MONTHS"
        );

        assertTrue("Should detect membership expiring within 30 days",
                expiringMembership.isExpiringIn30Days());

        // Test membership expiring in 35 days
        Membership nonExpiringMembership = new Membership(
                TEST_USER_ID,
                LocalDate.now().plusDays(35),
                "THREE_MONTHS"
        );

        assertFalse("Should not detect membership expiring after 30 days",
                nonExpiringMembership.isExpiringIn30Days());
    }

    /**
     * Verify that membership renewal correctly updates both the membership
     * length and expiration date.
     */
    @Test
    public void testMembershipRenewal() {
        // Start with expired membership
        Membership expiredMembership = new Membership(
                TEST_USER_ID,
                LocalDate.now().minusDays(1),
                "THREE_MONTHS"
        );

        // Renew for one year
        expiredMembership.renewMembership("ONE_YEAR");

        assertTrue("Renewed membership should be active",
                expiredMembership.checkStatus());

        // Verify expiration date is one year from now
        LocalDate expectedExpiration = LocalDate.now().plusYears(1);
        assertEquals("Expiration date should be one year from renewal",
                expectedExpiration, expiredMembership.getExpirationDate());
    }

    /**
     * Test edge cases for membership renewal, including same-day renewals
     * and renewals of active memberships.
     */
    @Test
    public void testRenewalEdgeCases() {
        // Renew an active membership
        membership.renewMembership("THREE_YEARS");

        LocalDate expectedExpiration = LocalDate.now().plusYears(3);
        assertEquals("Three year renewal should set correct expiration",
                expectedExpiration, membership.getExpirationDate());

        // Verify immediate status
        assertTrue("Renewed membership should be immediately active",
                membership.checkStatus());
    }
}