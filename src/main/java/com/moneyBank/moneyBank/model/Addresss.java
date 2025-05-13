package com.moneyBank.moneyBank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Addresss {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name ="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )

    @Column(name = "id", updatable = false,nullable = false)
    private UUID id;
    private City city;
    private String postcode;
    private String addressDetails;

    public Addresss() {
    }

    public Addresss(UUID id, City city, String postcode, String addressDetails) {
        this.id = id;
        this.city = city;
        this.postcode = postcode;
        this.addressDetails = addressDetails;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addresss address = (Addresss) o;
        return Objects.equals(id, address.id) && city == address.city && Objects.equals(postcode, address.postcode) && Objects.equals(addressDetails, address.addressDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, postcode, addressDetails);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city=" + city +
                ", postcode='" + postcode + '\'' +
                ", addressDetails='" + addressDetails + '\'' +
                '}';
    }
}
