package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.*;

public class Cart implements Serializable {

    private final Map<Product, Integer> products = new HashMap<>();
    private final List<Product> unavailableProducts = new ArrayList<>();
    private Shopper shopper;

    private static final long serialVersionUID = 1L;

    private static final List<Cart> extent = new ArrayList<>();
    static { ExtentManager.registerExtent(Cart.class.getSimpleName(), extent); }

    public Cart(Shopper shopper) {
        setShopper(shopper);
        extent.add(this);
    }

    private void setShopper(Shopper shopper) {
        if (shopper == null)
            throw new IllegalArgumentException("Shopper cannot be null");
        this.shopper = shopper;
    }

    public Shopper getOwner() {
        return shopper;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");

        products.put(product, products.getOrDefault(product, 0) + quantity);
        if (!product.isAvailability())
            unavailableProducts.add(product);
    }

    public void changeQuantity(Product product, int newQuantity) {
        if (newQuantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        if (newQuantity == 0) products.remove(product);
        else products.put(product, newQuantity);
    }

    public void clear() {
        products.clear();
        unavailableProducts.clear();
    }

    public int getQuantityOfProducts() {
        return products.values().stream().mapToInt(i -> i).sum();
    }

    public Map<Product, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public List<Product> getUnavailableProducts() {
        return Collections.unmodifiableList(unavailableProducts);
    }

    public static List<Cart> getExtent() {
        return Collections.unmodifiableList(extent);
    }
    public static List<Cart> getExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadExtent(List<Cart> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
