package com.gymmanager.test;

import com.gymmanager.model.Membership;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class MembershipTest {
    private Membership membership;
    private static final String USER_ID = "TEST123";

    @Before
    public void setUp() {
        membership = new Membership(USER_ID, LocalDate.now().plusMonths(6), "SIX_MONTHS");
    }

    @Test
    public void testNewMembershipIsActive() {
        assertTrue("New membership should be active", membership.checkStatus());
    }

    @Test
    public void testExpiredMembership() {
        Membership expiredMembership = new Membership(USER_ID,
                LocalDate.now().minusDays(1), "THREE_MONTHS");
        assertFalse("Expired membership should be inactive",
                expiredMembership.checkStatus());
    }

    @Test
    public void testMembershipRenewal() {
        LocalDate originalDate = membership.getExpirationDate();
        membership.renewMembership("ONE_YEAR");
        assertTrue("Membership should be active after renewal",
                membership.checkStatus());
        assertTrue("New expiration date should be later than original",
                membership.getExpirationDate().isAfter(originalDate));
    }

    @Test
    public void testDifferentRenewalPeriods() {
        // Test THREE_MONTHS renewal
        membership.renewMembership("THREE_MONTHS");
        assertEquals("Three months renewal should be accurate",
                LocalDate.now().plusMonths(3), membership.getExpirationDate());

        // Test SIX_MONTHS renewal
        membership.renewMembership("SIX_MONTHS");
        assertEquals("Six months renewal should be accurate",
                LocalDate.now().plusMonths(6), membership.getExpirationDate());

        // Test ONE_YEAR renewal
        membership.renewMembership("ONE_YEAR");
        assertEquals("One year renewal should be accurate",
                LocalDate.now().plusYears(1), membership.getExpirationDate());

        // Test THREE_YEARS renewal
        membership.renewMembership("THREE_YEARS");
        assertEquals("Three years renewal should be accurate",
                LocalDate.now().plusYears(3), membership.getExpirationDate());
    }

    @Test
    public void testExpiringIn30Days() {
        // Test membership that expires in 15 days
        Membership expiringMembership = new Membership(USER_ID,
                LocalDate.now().plusDays(15), "THREE_MONTHS");
        assertTrue("Should identify membership expiring within 30 days",
                expiringMembership.isExpiringIn30Days());

        // Test membership that expires in 45 days
        Membership notExpiringMembership = new Membership(USER_ID,
                LocalDate.now().plusDays(45), "THREE_MONTHS");
        assertFalse("Should not identify membership expiring after 30 days",
                notExpiringMembership.isExpiringIn30Days());
    }

    @Test
    public void testSameDayExpiration() {
        Membership sameDayMembership = new Membership(USER_ID,
                LocalDate.now(), "THREE_MONTHS");
        assertTrue("Membership expiring today should still be active",
                sameDayMembership.checkStatus());
    }

    @Test
    public void testGetUserID() {
        assertEquals("UserID should match constructor input",
                USER_ID, membership.getUserID());
    }
}