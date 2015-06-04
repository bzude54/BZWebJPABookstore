package edu.uwpce.bzbookstore.service;


import edu.uwpce.bzbookstore.model.CreditCard;
import edu.uwpce.bzbookstore.model.Customer;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bzude on 4/27/15.
 */
public interface CustomerService {

    List<Customer> getCustomers();

    void setCustomers(List<Customer> customers);

    int addCustomer(Customer customer);

    Customer getCustomerByUsername(String username);

    Customer getCustomerById(int customerid);

    void updateCustomer(Customer customer);

    boolean deleteCustomer(int customerid);


    Customer addCreditCardsToUsername(String username, Set<CreditCard> cards);

    Customer deleteCreditCardFromUsername(String username, CreditCard card);


//    Customer addPhoneToUsername(String username, Phone phone);

    Customer updateNameAndAddress(Integer userId, String lastName, String street);

    void deleteCustomer(Integer customerId);

}
