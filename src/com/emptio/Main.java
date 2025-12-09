package com.emptio;

import com.emptio.model.*;
import com.emptio.persistence.PersistenceService;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== STARTING SYSTEM TEST ===");

        // ---------------------- SHOPPER + CART ----------------------
        ShippingAddress addr1 = new ShippingAddress("Main St", "CityX", "CountryY", "12345");
        Shopper shopper = new Shopper("John Doe", "john@test.com", addr1, "john", "pwd");
        System.out.println("Created Shopper: " + shopper.getFullName());

        Merchant merchant = new Merchant("Alice Shop", "alice@shop.com", addr1, "alice", "pwd", "123-456");
        System.out.println("Created Merchant: " + merchant.getFullName());

        Product product1 = new Product(merchant, "Game1", 49.99, true, "Popular Game");
        Product product2 = new Product(merchant, "Game2", 59.99, true, "New Release");
        shopper.getCart().addProduct(product1, 2);
        shopper.getCart().addProduct(product2, 1);
        System.out.println("Shopper's cart contains products: " + shopper.getCart().getProducts().keySet());

        // ---------------------- ADVERTISER + CAMPAIGN ----------------------
        Advertiser advertiser = new Advertiser("Bob Ads", "bob@ads.com", addr1, "bob", "pwd", "AdCo");
        System.out.println("Created Advertiser: " + advertiser.getFullName());

        Campaign campaign = new Campaign("Holiday Campaign", 5, 1000);
        campaign.setAdvertiser(advertiser);
        DurationDate duration = new DurationDate(LocalDate.now(), LocalDate.now().plusDays(10));
        campaign.setDuration(duration);
        campaign.addProduct(product1);
        campaign.addProduct(product2);

        System.out.println("Created Campaign: " + campaign.getNameOfCampaign());
        System.out.println("Campaign products: " + campaign.getProducts());
        System.out.println("Campaign fee: " + campaign.getCampaignFee());

        // ---------------------- MODERATORS ----------------------
        Admin admin = new Admin("Admin User", "admin@test.com", addr1, "admin", "pwd", "EMP001");
        MarketModerator marketMod = new MarketModerator("Market Mod", "market@test.com", addr1, "market", "pwd", "EMP002");
        Support support = new Support("Support Guy", "support@test.com", addr1, "support", "pwd", "EMP003");

        System.out.println("Created Admin: " + admin.getFullName());
        System.out.println("Created MarketModerator: " + marketMod.getFullName());
        System.out.println("Created Support: " + support.getFullName());

        // MarketModerator operations
        marketMod.addSuspectedMerchant(merchant);
        System.out.println("MarketModerator suspected merchants: " + marketMod.getSuspectedMerchantByEmail("alice@shop.com").getFullName());

        // ---------------------- PERSISTENCE ----------------------
        try {
            System.out.println("Saving all extents to disk...");
            PersistenceService.saveAllExtents();
            System.out.println("Save complete!");

            System.out.println("Clearing all extents in memory...");
            Shopper.loadExtent(null);
            Merchant.loadExtent(null);
            Advertiser.loadExtent(null);
            Campaign.loadExtent(null);
            Moderator.loadModeratorExtent(null);
            Admin.loadAdminExtent(null);
            MarketModerator.loadMarketModeratorExtent(null);
            Support.loadSupportExtent(null);
            System.out.println("All extents cleared in memory.");

            System.out.println("Loading all extents from disk...");
            PersistenceService.loadAllExtents();
            System.out.println("Load complete!");
            System.out.println("Shopper extent size: " + Shopper.getShopperExtent().size());
            System.out.println("Merchant extent size: " + Merchant.getMerchantExtent().size());
            System.out.println("Campaign extent size: " + Campaign.getCampaignExtent().size());
            System.out.println("Admin extent size: " + Admin.getAdminExtent().size());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // ---------------------- FINAL STATE ----------------------
        System.out.println("Final State Checks:");
        System.out.println("Shopper in memory: " + Shopper.getShopperExtent());
        System.out.println("Admin in memory: " + Admin.getAdminExtent());
        System.out.println("Campaign in memory: " + Campaign.getCampaignExtent());

        System.out.println("=== SYSTEM TEST COMPLETE ===");
    }
}
