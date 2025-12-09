package com.emptio.tests;

import com.emptio.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    void testUserValidation() {
        ShippingAddress addr = new ShippingAddress("Street", "City", "Country", "12345");

        // Shopper: empty fullName
        assertThrows(IllegalArgumentException.class, () -> new Shopper("", "email@test.com", addr, "login", "pwd"));

        // Shopper: invalid email
        assertThrows(IllegalArgumentException.class, () -> new Shopper("Name", "invalidEmail", addr, "login", "pwd"));

        // Advertiser: empty email
        assertThrows(IllegalArgumentException.class, () -> new Advertiser("Name", "", addr, "login", "pwd", "Company"));

        // Advertiser optional company name
        Advertiser adv = new Advertiser("Name", "adv@test.com", addr, "login", "pwd", null);
        assertNull(adv.getCompanyName());

        adv.setCompanyName("Company1");
        assertEquals("Company1", adv.getCompanyName());
        assertThrows(IllegalArgumentException.class, () -> adv.setCompanyName(""));
    }
}
