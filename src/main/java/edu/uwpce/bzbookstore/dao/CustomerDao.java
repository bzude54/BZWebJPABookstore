package edu.uwpce.bzbookstore.dao;


import edu.uwpce.bzbookstore.model.Customer;

import java.util.List;

/**
 * Created by bzude on 4/26/15.
 */
public interface CustomerDao {

    // CRUD :  IInsert  and update
    Customer save(Customer customer);


    // Queries
    Customer findById(Integer id);

    Customer findByUsername(String username);

    List<Customer> findAll();

    void deleteCustomerById(Integer id);

    String printCustomerInfo(Integer id);  // final implementation in CustomerServiceImpl but implemented here due to assignment requirements



//    Customer addCreditCard(String username, CreditCard card);  method implemented in CustomerServiceImpl

//    Customer deleteCreditCard(String username, CreditCard card); method implemented in CustomerServiceImpl

}
