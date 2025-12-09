package com.emptio.tests;

import com.emptio.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegularTests {

    @Test
    void testRegularCreation() {
        ShippingAddress addr =
                new ShippingAddress("Street", "City", "Country", "12345");

        Regular regular = new Regular(
                "Alice Brown",
                "alice@test.com",
                addr,
                "aliceLogin",
                "pwd",
                "EMP001"
        );

        assertEquals("EMP001", regular.getEmployeeNumber());
        assertTrue(Moderator.getModeratorExtent().contains(regular));
    }

    @Test
    void testInvalidEmployeeNumber() {
        ShippingAddress addr =
                new ShippingAddress("Street", "City", "Country", "12345");

        assertThrows(IllegalArgumentException.class, () ->
                new Regular(
                        "Alice",
                        "alice@test.com",
                        addr,
                        "login",
                        "pwd",
                        ""
                )
        );
    }
}
