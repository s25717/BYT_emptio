package com.emptio.tests;

import com.emptio.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MerchantTests {

    @Test
    void testBankAccountValidation() {
        ShippingAddress addr = new ShippingAddress("Street", "City", "Country", "12345");
        Merchant m = new Merchant("Merchant1", "m1@test.com", addr, "login", "pwd", "BA123");

        assertEquals("BA123", m.getBankAccountNumber());
        assertThrows(IllegalArgumentException.class, () -> m.setBankAccountNumber(""));
    }

    @Test
    void testExtent() {
        ShippingAddress addr = new ShippingAddress("Street", "City", "Country", "12345");
        Merchant m = new Merchant("Merchant2", "m2@test.com", addr, "login", "pwd", "BA456");
        assertTrue(Merchant.getMerchantExtent().contains(m));
    }
}
