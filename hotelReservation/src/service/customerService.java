package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The customerService Class is a service class that will instantiate a Singleton object
 * This singleton object is called instance and can be "communicated" with in the Resource layer
 * through getInstance(). This class will also create a HashMap called customers that will have an Email key and a Customer value
 *
 * This Singleton object can then be used to add a Customer in the application, get a particular Customer
 * from the application, or get ALL Customers from the application
 */
public class customerService {

    //create the only instance of this Singleton class
    private static customerService instance  = new customerService();

    //this private constructor prevents the app from creating the class instance
    private customerService() {};

    //method that can be called in the resource layer to leverage this class instance
    public static customerService getInstance() {
        return instance;
    }

    //HashMap created for use by the instance's methods detailed below
    private final Map<String, Customer> customers = new HashMap<String, Customer>();

    public void addCustomer(String email, String firstName, String lastName) {
        customers.put(email, new Customer (firstName, lastName, email));
    }

    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
