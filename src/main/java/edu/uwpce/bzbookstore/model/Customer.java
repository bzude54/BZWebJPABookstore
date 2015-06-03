package edu.uwpce.bzbookstore.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bzude on 4/26/15.
 */
@Entity
@Table(name = "Customer")
public class Customer implements Serializable {

    static final Logger log = LoggerFactory.getLogger(Customer.class);


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String userName;

 /*   @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID") // foreign key on CREDITCARD table to map back to CUSTOMER table
*/
    @OneToMany(mappedBy="customer",orphanRemoval = true ,fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private Set<CreditCard> creditCards = new TreeSet<>();

    // bi-directional , USERS table has a foreign key reference to the ADDRESS table
    @OneToMany(mappedBy="customer",orphanRemoval = true ,fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<Address> addresses;


    // private List<Phone> phoneNumbers;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }



    public boolean addCreditCard(CreditCard card) {
        boolean addcard = true;
        for (CreditCard testcard : creditCards) {
            if (testcard.equals(card)) {
                addcard = false;
            }
        }

        if (addcard) {
            creditCards.add(card);
        } else {
            log.error("THis credit card already exists");
        }

        return addcard;

    }

    public boolean deleteCreditCard(CreditCard card) {
        boolean deletecard = false;
        for (CreditCard testcard : creditCards) {
            if (card.compareTo(testcard) == 0) {
                deletecard = true;
            }
        }

        if (deletecard) {
            creditCards.remove(card);
        } else {
            log.error("This credit card does not exist.");
        }

        return deletecard;

    }



    public boolean addAddress(Address address) {
        boolean addAddress = true;
        for (Address testaddress : addresses) {
            if (testaddress.equals(address)) {
                addAddress = false;
            }
        }

        if (addAddress) {
            addresses.add(address);
        } else {
            log.error("THis address already exists");
        }

        return addAddress;

    }


    public boolean deleteAddress(Address address) {
        boolean deleteAddress = false;
        int addrindex;
        for (Address testaddress : addresses) {
            if (testaddress.equals(address)) {
                deleteAddress = true;
                addresses.remove(address);
                break;
            }
        }

        if (!deleteAddress) {
            log.error("THis address does not exists");
        }

        return deleteAddress;

    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }


    public static class Builder {
        private Customer customer;

        public Builder() {
            customer = new Customer();
        }

        public Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            customer.setId(id);
            return this;
        }

        public Builder firstname(String firstname) {
            customer.setFirstName(firstname);
            return this;
        }

        public Builder lastname(String lastname) {
            customer.setLastName(lastname);
            return this;
        }

        public Builder username(String username) {
            customer.setUserName(username);
            return this;
        }


        public Customer build() {
            //validate ??;
            return customer;
        }
    }


}
