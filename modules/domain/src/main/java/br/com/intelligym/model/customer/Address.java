package br.com.intelligym.model.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    public Address() {
    }

    public Address(String street, String number, String city, String state, String country, String zipCode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }
}

