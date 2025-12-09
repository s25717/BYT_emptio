package com.emptio.tests;
import com.emptio.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTests {

    private Admin admin;

    @BeforeEach
    void setup() {
        ShippingAddress addr =
                new ShippingAddress("Street", "City", "Country", "12345");

        admin = new Admin(
                "Alice Admin",
                "admin@test.com",
                addr,
                "admin",
                "pwd",
                "EMP-001"
        );
    }

    @Test
    void adminShouldBeModerator() {
        assertTrue(admin instanceof Moderator);
    }

    @Test
    void adminHasEmployeeNumber() {
        assertEquals("EMP-001", admin.getEmployeeNumber());
    }

    @Test
    void adminIsAddedToExtent() {
        assertTrue(Admin.getAdminExtent().contains(admin));
    }
}
