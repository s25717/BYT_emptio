package com.emptio.model;

import com.emptio.persistence.ExtentManager;
import java.io.Serializable;
import java.util.*;

public class Merchant extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bankAccountNumber;
    private List<Product> products = new ArrayList<>(); // each merchant owns multiple products

    private static final List<Merchant> extent = new ArrayList<>();

    static { ExtentManager.registerExtent(Merchant.class.getSimpleName(), extent); }

    public Merchant(String fullName, String email, ShippingAddress shippingAddress,
                    String login, String password, String bankAccountNumber) {
        super(fullName, email, null, shippingAddress, login, password);
        setBankAccountNumber(bankAccountNumber);
        extent.add(this);
    }

    public String getBankAccountNumber() { return bankAccountNumber; }

    public void setBankAccountNumber(String bankAccountNumber) {
        if (bankAccountNumber == null || bankAccountNumber.isBlank())
            throw new IllegalArgumentException("Bank account number cannot be empty");
        this.bankAccountNumber = bankAccountNumber;
    }

    // -------- PRODUCT MANAGEMENT --------
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        products.add(product);
    }

    public void viewInventory() {
        System.out.println("Inventory for merchant " + getFullName() + ":");
        for (Product p : products) {
            System.out.println("- " + p.getTitle() + " | Price: " + p.getPrice() + " | Available: " + p.isAvailability());
        }
    }

    public void editProductInformation(Product product, String newTitle, double newPrice, boolean newAvailability, String newDescription) {
        if (!products.contains(product)) throw new IllegalArgumentException("Product does not belong to this merchant");
        product.setTitle(newTitle);
        product.setPrice(newPrice);
        product.setAvailability(newAvailability);
        product.setDescription(newDescription);
    }

    // -------- EXTENT METHODS --------
    public static List<Merchant> getMerchantExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Merchant> getExtentForSave() { return new ArrayList<>(extent); }

    public static void loadExtent(List<Merchant> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
