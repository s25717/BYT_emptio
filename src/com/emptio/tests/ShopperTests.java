package com.emptio.tests;

import com.emptio.model.*;
import com.emptio.persistence.PersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopperTests {

    private Shopper shopper;
    private Product product1;
    private Merchant merchant;

    @BeforeEach
    void setup() {
        ShippingAddress addr = new ShippingAddress("Street", "City", "Country", "12345");
        shopper = new Shopper("John Doe", "john@test.com", addr, "john", "pwd");
        merchant = new Merchant(
                "Seller",
                "seller@test.com",
                addr,
                "sellerLogin",
                "pwd",
                "PL123456789"
        );
        product1 = new Product(
                merchant,
                "Game1",
                49.99,
                true,
                null
        );
    }

    @Test
    void testShopperCreation() {
        assertEquals("John Doe", shopper.getFullName());
        assertEquals("john@test.com", shopper.getEmail());
        assertNotNull(shopper.getCart());
        assertTrue(Shopper.getShopperExtent().contains(shopper));
    }

    @Test
    void testChangeProductQuantityValidation() {
        assertThrows(IllegalArgumentException.class, () -> shopper.changeProductQuantity(-5));
    }

    @Test
    void testCartOperations() {
        shopper.getCart().addProduct(product1, 2);

        // Проверяем количество
        assertEquals(2, shopper.getCart().getProducts().get(product1));

        // Проверяем наличие продукта в Map
        assertTrue(shopper.getCart().getProducts().containsKey(product1));

        // Проверяем owner
        assertEquals(shopper, shopper.getCart().getOwner());
    }

    @Test
    void testExtentPersistence() throws IOException, ClassNotFoundException {
        PersistenceService.saveAllExtents();

        // Clear extent
        Shopper.loadExtent(null);
        assertEquals(0, Shopper.getShopperExtent().size());

        // Load extent
        PersistenceService.loadAllExtents();
        List<Shopper> loaded = Shopper.getShopperExtent();
        assertTrue(loaded.stream().anyMatch(s -> s.getFullName().equals("John Doe")));
    }
}
