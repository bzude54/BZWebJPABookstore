package edu.uwpce.bzbookstore.dao;


import edu.uwpce.bzbookstore.model.Customer;

import java.util.List;

/**
 * Created by bzude on 4/26/15.
 */
public class AbstractCustomerDao implements CustomerDao {


    public Customer save(Customer customer) {
        throw new UnsupportedOperationException();
    }

    public Customer findById(Integer id) {
        throw new UnsupportedOperationException();
    }

    public Customer findByUsername(String username) {
        throw new UnsupportedOperationException();
    }

    public List<Customer> findAll() {
        throw new UnsupportedOperationException();
    }

    public void deleteCustomerById(Integer id) {
        throw new UnsupportedOperationException();

    }

    public String printCustomerInfo(Integer id) {
        throw new UnsupportedOperationException();
    }


/*
    method implemented in CustomerServiceImpl
    public Customer addCreditCard(CreditCard card) {
        throw new UnsupportedOperationException();
    }
*/


 /*
    method implemented in CustomerServiceImpl
    public Customer deleteCreditCard(CreditCard card) {
        throw new UnsupportedOperationException();
    }
*/

}
