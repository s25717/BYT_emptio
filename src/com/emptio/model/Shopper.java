package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shopper extends User implements Serializable {
    private Cart cart;
    private static final List<Shopper> extent = new ArrayList<>();
    private Shopper shopper;
    private static final long serialVersionUID = 1L;

    static { ExtentManager.registerExtent(Shopper.class.getSimpleName(), extent); }

    public Shopper(String fullName, String email, ShippingAddress shippingAddress,
                   String login, String password) {
        super(fullName, email, null, shippingAddress, login, password);
        this.cart = new Cart(this);
        extent.add(this);
    }

    public Cart getCart() { return cart; }

    public Shopper getOwner() {
        return shopper;
    }
    public void changeProductQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
    }

    public void requestPurchase() { }

    public static List<Shopper> getShopperExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Shopper> getExtentForSave() { return new ArrayList<>(extent); }

    public static void loadExtent(List<Shopper> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
