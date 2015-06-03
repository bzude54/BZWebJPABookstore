package edu.uwpce.bzbookstore.service;


import edu.uwpce.bzbookstore.dao.CustomerDao;
import edu.uwpce.bzbookstore.model.CreditCard;
import edu.uwpce.bzbookstore.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by bzude on 4/27/15.
 */
@Transactional
@org.springframework.stereotype.Service("service")
public class CustomerServiceImpl implements CustomerService {

    static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Resource(name = "customerDao")
    CustomerDao customerDao;

    @Override
    public Customer addCreditCardToUsername(String username, CreditCard card) {
        Customer customer = customerDao.findByUsername(username);
        int countCardsBefore = customer.getCreditCards().size();
        log.info("before credit card add, " + customer.getUserName() + " has " + countCardsBefore + " creditcard(s)");

        boolean result = customer.addCreditCard(card);
        log.info("after credit card add, " + customer.getUserName() + " has " + customer.getCreditCards().size() + " creditcard(s)");

        return customer;
    }

    @Override
    public Customer deleteCreditCardFromUsername(String username, CreditCard card) {
        Customer customer = customerDao.findByUsername(username);
        boolean result = customer.deleteCreditCard(card);
        customerDao.save(customer);
        return customer;
    }

    @Override
    public Customer updateNameAndAddress(Integer userId, String lastName, String street) {
        return null;
    }

    @Override
    public void deleteCustomer(Integer customerid) { }

    public String printCustomerInfo(Integer customerid) {
        Customer printcustomer = customerDao.findById(customerid);

        StringBuilder customerString = new StringBuilder();
        customerString.append("/n/n");
        customerString.append("Customer name: ");
        customerString.append(printcustomer.getFirstName() + " " + printcustomer.getLastName());
        customerString.append("/n/nCustomer username: ");
        customerString.append(printcustomer.getUserName());
        customerString.append("/n/nCustomer address: ");
        customerString.append(printcustomer.getAddresses().get(0).getStreet());
        customerString.append(" " + printcustomer.getAddresses().get(0).getCity());
        customerString.append(", " + printcustomer.getAddresses().get(0).getState());
        customerString.append(" " + printcustomer.getAddresses().get(0).getZip());
        customerString.append("/n/nCredit card: ");
        customerString.append(printcustomer.getCreditCards().iterator().next().toString());

        return customerString.toString();
    }
}
