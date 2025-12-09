package com.emptio.model;

import com.emptio.persistence.ExtentManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private double price;
    private boolean availability;
    private String description; // optional
    private Merchant merchant; // mandatory association
    private Campaign campaign; // optional association

    private static final List<Product> extent = new ArrayList<>();
    static { ExtentManager.registerExtent(Product.class.getSimpleName(), extent); }

    public Product(Merchant merchant, String title, double price, boolean availability, String description) {
        setMerchant(merchant);
        setTitle(title);
        setPrice(price);
        setAvailability(availability);
        setDescription(description);
        extent.add(this);
    }

    // derived
    public double getAdvertismentFee() {
        return campaign != null ? campaign.getCampaignFee() : 0;
    }

    // ---------------- GETTERS/SETTERS ----------------
    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty");
        this.title = title;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    public boolean isAvailability() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description != null && description.isBlank())
            throw new IllegalArgumentException("Description cannot be empty if provided");
        this.description = description;
    }

    public Merchant getMerchant() { return merchant; }
    private void setMerchant(Merchant merchant) {
        if (merchant == null) throw new IllegalArgumentException("Merchant cannot be null");
        this.merchant = merchant;
    }

    public Campaign getCampaign() { return campaign; }
    public void setCampaign(Campaign campaign) { this.campaign = campaign; }

    public static List<Product> getExtent() { return Collections.unmodifiableList(extent); }
    public static List<Product> getExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadExtent(List<Product> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
