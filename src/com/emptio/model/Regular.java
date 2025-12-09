package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Regular extends Moderator implements Serializable {

    private static final long serialVersionUID = 1L;

    // отдельный extent для подкласса
    private static final List<Regular> extent = new ArrayList<>();

    static {
        ExtentManager.registerExtent(Regular.class.getSimpleName(), extent);
    }

    public Regular(String fullName,
                   String email,
                   ShippingAddress shippingAddress,
                   String login,
                   String password,
                   String employeeNumber) {

        super(fullName, email, shippingAddress, login, password, employeeNumber);
        extent.add(this);
    }

    // ---------- DOMAIN METHODS (можно расширять) ----------

    public void reviewContent(String contentId) {
        if (contentId == null || contentId.isBlank())
            throw new IllegalArgumentException("Content id cannot be empty");
        // логика проверки контента (заглушка)
    }

    public void flagContent(String contentId, String reason) {
        if (contentId == null || contentId.isBlank())
            throw new IllegalArgumentException("Content id cannot be empty");
        if (reason == null || reason.isBlank())
            throw new IllegalArgumentException("Reason cannot be empty");
    }

    // ---------- EXTENT METHODS ----------

    public static List<Regular> getRegularExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Regular> getRegularExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadRegularExtent(List<Regular> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
