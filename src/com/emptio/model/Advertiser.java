package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.*;

public class Advertiser extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyName;
    private List<Campaign> campaigns;
    private static final List<Advertiser> extent = new ArrayList<>();

    static { ExtentManager.registerExtent(Advertiser.class.getSimpleName(), extent); }

    public Advertiser(String fullName, String email, ShippingAddress shippingAddress,
                      String login, String password, String companyName) {
        super(fullName, email, null, shippingAddress, login, password);
        setCompanyName(companyName);
        extent.add(this);
    }

    public String getCompanyName() { return companyName; }

    public List<Campaign> getCampaigns() {
        return Collections.unmodifiableList(campaigns);
    }
    public void setCompanyName(String companyName) {
        if (companyName != null && companyName.isBlank())
            throw new IllegalArgumentException("Optional company name cannot be empty");
        this.companyName = companyName;
    }

    public void addCampaign(Campaign campaign) {
        if (campaign == null) throw new IllegalArgumentException("Campaign cannot be null");
        if (!campaigns.contains(campaign)) {
            campaigns.add(campaign);
            campaign.setAdvertiser(this); // set back-reference
        }
    }

    public void editCampaignDetails(Campaign campaign, String newName, int newPrice, double newBudget) {
        if (campaign == null || !campaigns.contains(campaign))
            throw new IllegalArgumentException("Campaign not found for this advertiser");
        if (newName != null && !newName.isBlank()) campaign.setNameOfCampaign(newName);
        if (newPrice >= 0) campaign.setPricePerInteraction(newPrice);
        if ( newBudget > 0) campaign.setBudget(newBudget);
    }

    public void cancelCampaign(Campaign campaign) {
        if (campaign == null || !campaigns.contains(campaign)) return;
        campaigns.remove(campaign);
        campaign.setAdvertiser(null);
    }

    public static List<Advertiser> getAdvertiserExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Advertiser> getExtentForSave() { return new ArrayList<>(extent); }

    public static void loadExtent(List<Advertiser> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
