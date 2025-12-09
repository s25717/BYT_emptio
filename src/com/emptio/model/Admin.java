package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.*;

public class Admin extends Moderator implements Serializable {

    private static final long serialVersionUID = 1L;

    // multi-value attribute
    private final List<String> archiveLogs = new ArrayList<>();

    // own extent
    private static final List<Admin> extent = new ArrayList<>();

    static {
        ExtentManager.registerExtent(Admin.class.getSimpleName(), extent);
    }

    public Admin(String fullName,
                 String email,
                 ShippingAddress shippingAddress,
                 String login,
                 String password,
                 String employeeNumber) {

        super(fullName, email, shippingAddress, login, password, employeeNumber);
        extent.add(this);
    }

    // ---------- DOMAIN METHODS ----------

    public void suspendModerator(Moderator moderator) {
        if (moderator == null)
            throw new IllegalArgumentException("Moderator cannot be null");
        addArchiveLog("Moderator suspended: " + moderator.getEmployeeNumber());
    }

    public void addArchiveLog(String log) {
        if (log == null || log.isBlank())
            throw new IllegalArgumentException("Log cannot be empty");
        archiveLogs.add(log);
    }

    public void editArchiveLog(int index, String newValue) {
        if (newValue == null || newValue.isBlank())
            throw new IllegalArgumentException("New value cannot be empty");
        archiveLogs.set(index, newValue);
    }

    // ---------- GETTERS ----------

    public List<String> getArchiveLogs() {
        return Collections.unmodifiableList(new ArrayList<>(archiveLogs));
    }

    // ---------- EXTENT ----------

    public static List<Admin> getAdminExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Admin> getAdminExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadAdminExtent(List<Admin> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
