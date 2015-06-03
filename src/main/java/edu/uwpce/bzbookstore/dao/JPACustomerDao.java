package edu.uwpce.bzbookstore.dao;

import edu.uwpce.bzbookstore.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bzude on 4/26/15.
 */
@Repository("customerDao")
public class JPACustomerDao extends AbstractCustomerDao implements CustomerDao  {

    static final Logger log = LoggerFactory.getLogger(JPACustomerDao.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        } else {
            em.merge(customer);
        }
        em.flush();
        return customer;
    }

    @Override
    public Customer findById(Integer id) {

        return em.find(Customer.class, id);
    }

    @Override
    public Customer findByUsername(String username) {

        Customer customer = (Customer)em.createQuery(
                "SELECT c FROM Customer c WHERE c.userName = :userName", Customer.class)
                .setParameter("userName", username)
                .getSingleResult();

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = (List<Customer>)em.createQuery(
                "SELECT c FROM Customer c", Customer.class)
                .getResultList();
        return customers;
    }

    @Override
    public void deleteCustomerById(Integer id) {

        Customer customer = em.find(Customer.class, id);
        em.remove(customer);

    }

    // final implementation in CustomerServiceImpl but implemented here due to assignment requirements
    @Override
    public String printCustomerInfo(Integer customerid) {
        Customer printcustomer = this.findById(customerid);

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
