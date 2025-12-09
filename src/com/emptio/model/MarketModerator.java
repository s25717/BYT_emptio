package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.*;

public class MarketModerator extends Moderator implements Serializable {

    private static final long serialVersionUID = 1L;

    // Qualified association: email → Merchant
    private Map<String, Merchant> suspectedMerchants; // 0..* → 0..1
    private static final List<MarketModerator> extent = new ArrayList<>();

    static { ExtentManager.registerExtent(MarketModerator.class.getSimpleName(), extent); }

    public MarketModerator(String fullName, String email, ShippingAddress shippingAddress,
                           String login, String password, String employeeNumber) {
        super(fullName, email, shippingAddress, login, password, employeeNumber);
        this.suspectedMerchants = new HashMap<>();
        extent.add(this);
    }

    // ---------- QUALIFIED ASSOCIATION ----------

    public void addSuspectedMerchant(Merchant merchant) {
        if (merchant == null || merchant.getEmail() == null || merchant.getEmail().isBlank())
            throw new IllegalArgumentException("Invalid merchant");
        suspectedMerchants.put(merchant.getEmail(), merchant);
    }

    public Merchant getSuspectedMerchantByEmail(String email) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email cannot be empty");
        return suspectedMerchants.get(email);
    }

    // ---------- DOMAIN OPERATIONS ----------

    public void banUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");

        // logical domain action (no persistence flag required by model)
        // example: account access revoked
    }

    public void dischargeCompany(Merchant merchant) {
        if (merchant == null)
            throw new IllegalArgumentException("Merchant cannot be null");

        suspectedMerchants.remove(merchant.getEmail());
    }

    public void deleteProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null");

        // removing from extent represents deletion
        Product.getExtent().remove(product);
    }

    // ---------- EXTENT ----------

    public static List<MarketModerator> getMarketModeratorExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<MarketModerator> getMarketModeratorExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadMarketModeratorExtent(List<MarketModerator> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
