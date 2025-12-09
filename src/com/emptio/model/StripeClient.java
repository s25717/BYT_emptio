package com.emptio.model;

import java.io.Serializable;

public class StripeClient {

//    private static final long serialVersionUID = 1L;

    private String clientID;

    public StripeClient(String clientID) {
        setClientID(clientID);
    }

    public void setClientID(String clientID) {
        if (clientID == null || clientID.isBlank())
            throw new IllegalArgumentException("Client ID cannot be empty");
        this.clientID = clientID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getLinkToPaymentService() {
        return "https://stripe.com/pay/" + clientID;
    }

    public void transferFunds() {
        // simulate call
    }
}
