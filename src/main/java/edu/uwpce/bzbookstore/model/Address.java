package edu.uwpce.bzbookstore.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bzude on 4/28/15.
 */
@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable, Comparable<Address> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "ADDR_TYPE")
    private String addrtype;

    @Column(name = "STREET")
    private String street;


    @Column(name = "STREET2")
    private String street2;


    @Column(name = "CITY")
    private String city;


    @Column(name = "STATE")
    private String state;


    @Column(name = "ZIP")
    private String zip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddrtype() {
        return addrtype;
    }

    public void setAddrtype(String addrtype) {
        this.addrtype = addrtype;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!getCustomer().equals(address.getCustomer())) return false;
        if (!getStreet().equals(address.getStreet())) return false;
        if (getStreet2() != null ? !getStreet2().equals(address.getStreet2()) : address.getStreet2() != null)
            return false;
        if (!getCity().equals(address.getCity())) return false;
        if (!getState().equals(address.getState())) return false;
        return getZip().equals(address.getZip());

    }

    @Override
    public int hashCode() {
        int result = getCustomer().hashCode();
        result = 31 * result + getStreet().hashCode();
        result = 31 * result + (getStreet2() != null ? getStreet2().hashCode() : 0);
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getState().hashCode();
        result = 31 * result + getZip().hashCode();
        return result;
    }

    @Override
    public int compareTo(Address o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", customer=" + customer +
                ", address type=" + addrtype +
                ", street='" + street + '\'' +
                ", street2='" + street2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }

    public static class Builder {
        private Address address;

        public Builder() {
            address = new Address();
        }

        public Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            address.setId(id);
            return this;
        }

        public Builder customer(Customer customer) {
            address.setCustomer(customer);
            return this;
        }

        public Builder addrtype(String addrtype) {
            address.setAddrtype(addrtype);
            return this;
        }

        public Builder street(String street) {
            address.setStreet(street);
            return this;
        }

/*
        public Builder street(String street2) {
            address.setStreet2(street2);
            return this;
        }
*/

        public Builder city(String city) {
            address.setCity(city);
            return this;
        }

        public Builder state(String state) {
            address.setState(state);
            return this;
        }

        public Builder zip(String zip) {
            address.setZip(zip);
            return this;
        }

        public Address build() {
            //validate ??;
            return address;
        }


    }
}

