package com.rean.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {

    private String country;
    private String city;
    private String zip;

    public Address(String country, String city, String zip) {
        this.country = country;
        this.city = city;
        this.zip = zip;
    }

    public Address() {

    }

    public static Address of(String country, String city, String zip) {
        return new Address(country, city, zip);
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
