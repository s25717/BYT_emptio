package com.emptio.tests;

import com.emptio.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvertiserTests {

    @Test
    void testOptionalCompanyName() {
        ShippingAddress addr = new ShippingAddress("Street", "City", "Country", "12345");
        Advertiser adv = new Advertiser("Adv1", "adv@test.com", addr, "login", "pwd", null);
        assertNull(adv.getCompanyName());

        adv.setCompanyName("Company1");
        assertEquals("Company1", adv.getCompanyName());

        assertThrows(IllegalArgumentException.class, () -> adv.setCompanyName(""));
    }
}
