package com.emptio.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private String receiverBankInformation;
    private String payerInformation;
    private double value;
    private String currency;
    private TransactionStatus status;
    private LocalDate dateOfTransaction;

    private StripeClient stripeClient;

    // REFLEXIVE ASSOCIATION
    private static List<Transaction> extent = new ArrayList<>();

    public Transaction(String receiverBankInformation, String payerInformation,
                       double value, String currency, TransactionStatus status,
                       LocalDate dateOfTransaction, StripeClient stripeClient) {

        setReceiverBankInformation(receiverBankInformation);
        setPayerInformation(payerInformation);
        setValue(value);
        setCurrency(currency);
        setStatus(status);
        setDateOfTransaction(dateOfTransaction);
        setStripeClient(stripeClient);

        extent.add(this);
    }

    public static List<Transaction> getExtent() {
        return List.copyOf(extent);
    }

    // ---------------- VALIDATION ----------------

    public void setReceiverBankInformation(String v) {
        if (v == null || v.isBlank())
            throw new IllegalArgumentException("Receiver information cannot be empty");
        this.receiverBankInformation = v;
    }

    public void setPayerInformation(String v) {
        if (v == null || v.isBlank())
            throw new IllegalArgumentException("Payer information cannot be empty");
        this.payerInformation = v;
    }

    public void setValue(double v) {
        if (v <= 0)
            throw new IllegalArgumentException("Value must be positive");
        this.value = v;
    }

    public void setCurrency(String c) {
        if (c == null || c.isBlank())
            throw new IllegalArgumentException("Currency cannot be empty");
        this.currency = c;
    }

    public void setStatus(TransactionStatus s) {
        if (s == null) throw new IllegalArgumentException("Status cannot be null");
        this.status = s;
    }

    public void setDateOfTransaction(LocalDate d) {
        if (d == null) throw new IllegalArgumentException("Date cannot be null");
        if (d.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date cannot be in the future");
        this.dateOfTransaction = d;
    }

    public void setStripeClient(StripeClient sc) {
        if (sc == null) throw new IllegalArgumentException("Stripe client cannot be null");
        this.stripeClient = sc;
    }

    // BUSINESS LOGIC
    public void sendDataToStripe() {}
    public void receiveConfirmation() {}
    public static List<Transaction> getExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadExtent(List<Transaction> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
