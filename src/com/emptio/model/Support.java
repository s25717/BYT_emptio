package com.emptio.model;

import com.emptio.persistence.ExtentManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Support extends Moderator implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> ticketsHistory;
    private static final List<Support> extent = new ArrayList<>();

    static { ExtentManager.registerExtent(Support.class.getSimpleName(), extent); }

    public Support(String fullName, String email, ShippingAddress shippingAddress,
                   String login, String password, String employeeNumber) {

        super(fullName, email, shippingAddress, login, password, employeeNumber);
        this.ticketsHistory = new ArrayList<>();
        extent.add(this);
    }

    // ---------- BUSINESS METHODS ----------

    public void addTicket(String ticket) {
        if (ticket == null || ticket.isBlank())
            throw new IllegalArgumentException("Ticket cannot be empty");
        ticketsHistory.add(ticket);
    }

    public void closeTicket(String ticket) {
        if (ticket == null || ticket.isBlank()) throw new IllegalArgumentException("Ticket cannot be empty");
        ticketsHistory.add("Closed: " + ticket);
    }
    public List<String> getTicketsHistory() {
        return Collections.unmodifiableList(ticketsHistory);
    }

    // ---------- EXTENT ----------

    public static List<Support> getSupportExtent() {
        return Collections.unmodifiableList(new ArrayList<>(extent));
    }

    public static List<Support> getSupportExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadSupportExtent(List<Support> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
