// com/emptio/model/ShippingAddress.java
package com.emptio.model;

import java.io.Serializable;
import java.util.Objects;

public class ShippingAddress implements Serializable {
    private String street;
    private String city;
    private String country;
    private String postalCode;

//    private static final long serialVersionUID = 1L;
//????? VO
    public ShippingAddress(String street, String city, String country, String postalCode) {
        setStreet(street);
        setCity(city);
        setCountry(country);
        setPostalCode(postalCode);
    }

    public String getStreet() { return street; }
    public void setStreet(String street) {
        if (street == null || street.trim().isEmpty()) throw new IllegalArgumentException("Street cannot be empty");
        this.street = street;
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) throw new IllegalArgumentException("City cannot be empty");
        this.city = city;
    }

    public String getCountry() { return country; }
    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) throw new IllegalArgumentException("Country cannot be empty");
        this.country = country;
    }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) {
        if (postalCode == null || postalCode.trim().isEmpty()) throw new IllegalArgumentException("Postal code cannot be empty");
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) { return o == this || (o instanceof ShippingAddress && Objects.equals(((ShippingAddress)o).street, street) && Objects.equals(((ShippingAddress)o).postalCode, postalCode)); }

    @Override
    public int hashCode() { return Objects.hash(street, postalCode); }

    @Override
    public String toString() {
        return street + ", " + city + ", " + postalCode + ", " + country;
    }
}
