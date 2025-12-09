package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.*;

public class Moderator extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String employeeNumber; // mandatory
    private static final List<Moderator> extent = new ArrayList<>();

    static { ExtentManager.registerExtent(Moderator.class.getSimpleName(), extent); }

    public Moderator(String fullName, String email, ShippingAddress shippingAddress,
                     String login, String password, String employeeNumber) {
        super(fullName, email, null, shippingAddress, login, password);
        setEmployeeNumber(employeeNumber);
        extent.add(this);
    }

    public String getEmployeeNumber() { return employeeNumber; }

    public void setEmployeeNumber(String employeeNumber) {
        if (employeeNumber == null || employeeNumber.isBlank())
            throw new IllegalArgumentException("Employee number cannot be empty");
        this.employeeNumber = employeeNumber;
    }

    public static List<Moderator> getModeratorExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Moderator> getModeratorExtentForSave() { return new ArrayList<>(extent); }

    public static void loadModeratorExtent(List<Moderator> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
