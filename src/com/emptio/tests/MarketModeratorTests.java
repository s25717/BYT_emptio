package com.emptio.tests;
import com.emptio.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketModeratorTests {

    private MarketModerator moderator;
    private Merchant merchant;

    @BeforeEach
    void setup() {
        ShippingAddress addr =
                new ShippingAddress("Street", "City", "Country", "12345");

        moderator = new MarketModerator(
                "Mark Mod",
                "mod@test.com",
                addr,
                "mod",
                "pwd",
                "EMP-002"
        );

        merchant = new Merchant(
                "ShopLTD",
                "seller@test.com",
                addr,
                "LTDLogin",
                "ltd_pwd",
                "PL123456789"
        );
    }

    @Test
    void marketModeratorIsModerator() {
        assertTrue(moderator instanceof Moderator);
    }

    @Test
    void canAddAndRetrieveSuspectedMerchant() {
        moderator.addSuspectedMerchant(merchant);

        Merchant found =
                moderator.getSuspectedMerchantByEmail("merchant@test.com");

        assertEquals(merchant, found);
    }

    @Test
    void suspectedMerchantMustBeQualifiedByEmail() {
        assertNull(
                moderator.getSuspectedMerchantByEmail("unknown@test.com")
        );
    }

    @Test
    void marketModeratorIsAddedToExtent() {
        assertTrue(
                MarketModerator.getMarketModeratorExtent().contains(moderator)
        );
    }
}
