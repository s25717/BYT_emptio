package com.emptio.model;

import java.io.Serializable;
import java.util.*;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fullName;
    private String email;
    private String mobileNumber; // optional
    private ShippingAddress shippingAddress;
    private String login;
    private String password;

    protected User(String fullName, String email, String mobileNumber,
                   ShippingAddress shippingAddress, String login, String password) {
        setFullName(fullName);
        setEmail(email);
        setMobileNumber(mobileNumber);
        setShippingAddress(shippingAddress);
        setLogin(login);
        setPassword(password);
    }
    private static final List<User> extent = new ArrayList<>();

    // ---------------- GETTERS/SETTERS ----------------
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank())
            throw new IllegalArgumentException("Full name cannot be empty");
        this.fullName = fullName;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email");
        this.email = email;
    }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) {
        if (mobileNumber != null && mobileNumber.isBlank())
            throw new IllegalArgumentException("Mobile cannot be empty string");
        this.mobileNumber = mobileNumber;
    }

    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddress shippingAddress) {
        if (shippingAddress == null) throw new IllegalArgumentException("Shipping address is mandatory");
        this.shippingAddress = shippingAddress;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) {
        if (login == null || login.isBlank()) throw new IllegalArgumentException("Login cannot be empty");
        this.login = login;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password cannot be empty");
        this.password = password;
    }
    public static List<User> getUserExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadUserExtent(List<User> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
