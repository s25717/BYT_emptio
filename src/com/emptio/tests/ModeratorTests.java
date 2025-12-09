package com.emptio.tests;

import com.emptio.model.*;
import com.emptio.persistence.PersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ModeratorTests {

    private Admin admin;
    private MarketModerator marketMod;
    private Support support;

    @BeforeEach
    void setup() {
        ShippingAddress addr = new ShippingAddress("Street", "City", "Country", "12345");
        admin = new Admin("Alice Admin", "alice@admin.com", addr, "alice", "pwd", "E001");
        marketMod = new MarketModerator("Bob Market", "bob@market.com", addr, "bob", "pwd", "E002");
        support = new Support("Carol Support", "carol@support.com", addr, "carol", "pwd", "E003");
    }

    @Test
    void testAdminLogs() {
        admin.addArchiveLog("Log1");
        assertEquals(1, admin.getArchiveLogs().size());
        assertThrows(IllegalArgumentException.class, () -> admin.addArchiveLog(""));
    }

    @Test
    void testMarketModeratorMap() {
        Merchant m = new Merchant("M1", "m1@merchant.com", new ShippingAddress("S", "C", "Country", "0001"), "login", "pwd", "BA123");
        marketMod.addSuspectedMerchant(m);
        assertEquals(m, marketMod.getSuspectedMerchantByEmail("m1@merchant.com"));
        assertThrows(IllegalArgumentException.class, () -> marketMod.addSuspectedMerchant(null));
    }

    @Test
    void testSupportTickets() {
        support.closeTicket("Ticket1");
        assertEquals(1, support.getTicketsHistory().size());
        assertThrows(IllegalArgumentException.class, () -> support.closeTicket(""));
    }

    @Test
    void testPersistence() throws IOException, ClassNotFoundException {
        PersistenceService.saveAllExtents();

        Admin.loadAdminExtent(null);
        MarketModerator.loadMarketModeratorExtent(null);
        Support.loadSupportExtent(null);

        assertEquals(0, Admin.getAdminExtent().size());
        assertEquals(0, MarketModerator.getMarketModeratorExtent().size());
        assertEquals(0, Support.getSupportExtent().size());

        PersistenceService.loadAllExtents();

        assertTrue(Admin.getAdminExtent().contains(admin));
        assertTrue(MarketModerator.getMarketModeratorExtent().contains(marketMod));
        assertTrue(Support.getSupportExtent().contains(support));
    }
}
