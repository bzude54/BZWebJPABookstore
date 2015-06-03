package edu.uwpce.bzbookstore.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bzude on 4/26/15.
 */

@Entity
@Table(name = "CREDITCARD")
public class CreditCard implements Serializable, Comparable<CreditCard> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "CARD_TYPE")
    private String cardType;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

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


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;

        CreditCard that = (CreditCard) o;

        if (!(getCustomer().getId()).equals(that.getCustomer().getId())) return false;
        if (!getCardNumber().equals(that.getCardNumber())) return false;
        if (!getCardType().equals(that.getCardType())) return false;
        return getExpirationDate().equals(that.getExpirationDate());

    }


    @Override
    public int hashCode() {
        return cardNumber.hashCode();
    }
/*
    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getCardNumber().hashCode();
        result = 31 * result + getCardType().hashCode();
        result = 31 * result + getExpirationDate().hashCode();
        return result;
    }
*/


    public int compareTo(CreditCard othercard) {

        return cardNumber.compareTo(othercard.getCardNumber());
    }


    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", customerId=" + customer.getId() +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }


    public static class Builder {
        private CreditCard card;

        public Builder() {
            card = new CreditCard();
        }

        public Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            card.setId(id);
            return this;
        }

        public Builder customer(Customer customer) {
            card.setCustomer(customer);
            return this;
        }

        public Builder cardNumber(String cardNum) {
            card.setCardNumber(cardNum);
            return this;
        }

        public Builder cardType(String cardType) {
            card.setCardType(cardType);
            return this;
        }

        public Builder expireDate(Date expires) {
            card.setExpirationDate(expires);
            return this;
        }

        public CreditCard build() {
            //validate ??;
            return card;
        }
    }




}
