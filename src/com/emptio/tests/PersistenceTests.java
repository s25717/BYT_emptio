package com.emptio.tests;

import com.emptio.model.*;
import com.emptio.persistence.PersistenceService;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTests {

    private Advertiser advertiser;
    private Shopper shopper;
    private Merchant merchant;
    private Campaign campaign;

    @BeforeEach
    void setup() {
        ShippingAddress addr = new ShippingAddress("Street", "City", "Country", "12345");
        merchant = new Merchant("Merchant Name", "merchant@test.com", addr, "loginM", "pwdM", "1234567890");
        shopper = new Shopper("John Doe", "john@test.com", addr, "john", "pwd");
        advertiser = new Advertiser("Adv Name", "adv@test.com", addr, "advLogin", "advPwd", "Company");
        campaign = new Campaign("Campaign1", 10, 1000);
        advertiser.addCampaign(campaign);
        Product prod = new Product(merchant, "Game1", 49.99, true, null);
        campaign.addProduct(prod);
    }

    @Test
    void testSaveAndLoadExtents() throws IOException, ClassNotFoundException {
        // Save all extents
        PersistenceService.saveAllExtents();

        // Clear current extents manually to simulate fresh start
        Advertiser.loadExtent(List.of());
        Shopper.loadExtent(List.of());
        Merchant.loadExtent(List.of());
        Campaign.loadExtent(List.of());

        assertEquals(0, Advertiser.getAdvertiserExtent().size());
        assertEquals(0, Shopper.getShopperExtent().size());

        // Load extents
        PersistenceService.loadAllExtents();

        // Verify extents restored correctly
        assertEquals(1, Advertiser.getAdvertiserExtent().size());
        assertEquals("Adv Name", Advertiser.getAdvertiserExtent().get(0).getFullName());
        assertEquals(1, Advertiser.getAdvertiserExtent().get(0).getCampaigns().size());

        assertEquals(1, Shopper.getShopperExtent().size());
        assertEquals("John Doe", Shopper.getShopperExtent().get(0).getFullName());
    }

    @Test
    void testDoubleLoadingBug() throws IOException, ClassNotFoundException {
        // Save extents
        PersistenceService.saveAllExtents();

        // Load multiple times
        PersistenceService.loadAllExtents();
        PersistenceService.loadAllExtents();

        // Ensure no duplicates
        assertEquals(1, Advertiser.getAdvertiserExtent().size());
        assertEquals(1, Shopper.getShopperExtent().size());
        assertEquals(1, Merchant.getMerchantExtent().size());
    }

    @Test
    void testExtentPersistenceIntegrity() {
        // Derived attribute check after loading
        double expectedFee = advertiser.getCampaigns().get(0).getCampaignFee();
        assertEquals(10, expectedFee); // 10 pricePerInteraction * 1 product
    }
}
