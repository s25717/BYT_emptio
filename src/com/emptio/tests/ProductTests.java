package com.emptio.tests;

import com.emptio.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTests {

    private Merchant merchant;
    private Product product;

    @BeforeEach
    void setup() {
        ShippingAddress addr =
                new ShippingAddress("Street", "City", "Country", "12345");

        merchant = new Merchant(
                "Shop Owner",
                "shop@test.com",
                addr,
                "shopLogin",
                "pwd",
                "PL123456789"
        );

        product = new Product(
                merchant,
                "Game 1",
                49.99,
                true,
                null           // optional description
        );
    }

    @Test
    void testProductCreation() {
        assertEquals("Game 1", product.getTitle());
        assertEquals(49.99, product.getPrice());
        assertTrue(product.isAvailability());
        assertEquals(merchant, product.getMerchant());
        assertNull(product.getDescription());
    }

    @Test
    void testInvalidTitle() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(merchant, "", 10, true, null));
    }

    @Test
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(merchant, "Game", -1, true, null));
    }

    @Test
    void testOptionalDescriptionValidation() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(merchant, "Game", 10, true, ""));
    }

    @Test
    void testDerivedAdvertisementFee() {
        assertEquals(0, product.getAdvertismentFee());
    }

    @Test
    void testExtent() {
        assertTrue(Product.getExtent().contains(product));
    }
}
