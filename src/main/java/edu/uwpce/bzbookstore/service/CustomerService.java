package edu.uwpce.bzbookstore.service;


import edu.uwpce.bzbookstore.model.CreditCard;
import edu.uwpce.bzbookstore.model.Customer;

/**
 * Created by bzude on 4/27/15.
 */
public interface CustomerService {


    Customer addCreditCardToUsername(String username, CreditCard card);

    Customer deleteCreditCardFromUsername(String username, CreditCard card);


//    Customer addPhoneToUsername(String username, Phone phone);

    Customer updateNameAndAddress(Integer userId, String lastName, String street);

    void deleteCustomer(Integer customerId);

}
