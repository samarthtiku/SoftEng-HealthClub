package com.gymmanager.test;

import org.junit.Test;
import static org.junit.Assert.*;
import com.gymmanager.model.Membership;
import com.gymmanager.enums.MembershipLength;
import java.time.LocalDate;

public class MembershipTest {

    @Test
    public void testMembershipStatus() {
        Membership membership = new Membership("TEST123",
                LocalDate.now().plusMonths(6),
                MembershipLength.SIX_MONTHS);

        assertTrue("Active membership should return true", membership.checkStatus());

        Membership expiredMembership = new Membership("TEST124",
                LocalDate.now().minusDays(1),
                MembershipLength.THREE_MONTHS);

        assertFalse("Expired membership should return false", expiredMembership.checkStatus());
    }

    @Test
    public void testExpiryNotification() {
        Membership membership = new Membership("TEST125",
                LocalDate.now().plusDays(25),
                MembershipLength.ONE_YEAR);

        assertTrue("Should return true for membership expiring within 30 days",
                membership.isExpiringIn30Days());
    }
}