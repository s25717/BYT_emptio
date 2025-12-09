package com.emptio.model;

import com.emptio.persistence.ExtentManager;
import java.io.Serializable;
import java.util.*;

public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nameOfCampaign;
    private int pricePerInteraction;
    private double budget;

    private DurationDate duration; // complex attribute
    private Advertiser advertiser; // 1 → 0..*
    private List<Product> products = new ArrayList<>(); // 0..* aggregation

    private static final List<Campaign> extent = new ArrayList<>();

    static { ExtentManager.registerExtent(Campaign.class.getSimpleName(), extent); }

    // Constructor
    public Campaign(String nameOfCampaign, int pricePerInteraction, double budget) {
        setNameOfCampaign(nameOfCampaign);
        setPricePerInteraction(pricePerInteraction);
        setBudget(budget);
        extent.add(this);
    }

    // -------- GETTERS / SETTERS --------
    public String getNameOfCampaign() { return nameOfCampaign; }
    public void setNameOfCampaign(String nameOfCampaign) {
        if (nameOfCampaign == null || nameOfCampaign.isBlank())
            throw new IllegalArgumentException("Campaign name cannot be empty");
        this.nameOfCampaign = nameOfCampaign;
    }

    public int getPricePerInteraction() { return pricePerInteraction; }
    public void setPricePerInteraction(int pricePerInteraction) {
        if (pricePerInteraction < 0) throw new IllegalArgumentException("Price per interaction cannot be negative");
        this.pricePerInteraction = pricePerInteraction;
    }

    public double getBudget() { return budget; }
    public void setBudget(double budget) {
        if (budget < 0) throw new IllegalArgumentException("Budget cannot be negative");
        this.budget = budget;
    }

    public DurationDate getDuration() { return duration; }
    public void setDuration(DurationDate duration) {
        if (duration == null) throw new IllegalArgumentException("Duration cannot be null");
        this.duration = duration;
    }

    public Advertiser getAdvertiser() { return advertiser; }
    public void setAdvertiser(Advertiser advertiser) {
        if (advertiser == null) throw new IllegalArgumentException("Advertiser cannot be null");
        this.advertiser = advertiser;
    }

    public List<Product> getProducts() { return Collections.unmodifiableList(products); }

    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        products.add(product);
        product.setCampaign(this); // link product to campaign
    }

    // Derived attribute: calculate total campaign fee
    public double getCampaignFee() {
        return products.stream().mapToDouble(Product::getPrice).sum() * pricePerInteraction;
    }

    // -------- EXTENT METHODS --------
    public static List<Campaign> getCampaignExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Campaign> getExtentForSave() { return new ArrayList<>(extent); }

    public static void loadExtent(List<Campaign> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
