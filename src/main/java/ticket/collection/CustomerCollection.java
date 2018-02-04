package ticket.collection;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;

import ticket.common.entity.Customer;

/**
 * Collection for lookup of customers
 *
 * Data is persisted only in memory.
 * 
 * Could refactor this later to handle saving to the database or 
 * other persistent storage medium
 * 
 * @author Henry Hellbusch <hhellbusch@gmail.com>
 * @since  2018.01.20
 */
public class CustomerCollection
{
	private HashMap<String, Customer> customers;
	private final AtomicInteger counter = new AtomicInteger();

	public CustomerCollection()
	{
		this.customers = new HashMap<String, Customer>();
	}

	/**
	 * Saves a customer for later lookup
	 * @param customer 
	 */
	public void saveCustomer(Customer customer)
	{
		//TODO add data validation - require email to be set
		this.customers.put(customer.getEmail(), customer);
		// if customer already has an id, dont need to set one
		if (customer.getId() != null) {
			return;
		}

		//get the next id for a customer
		customer.setId(this.counter.incrementAndGet());
	}

	public Customer findByEmail(String email)
	{
		return this.customers.get(email.toLowerCase());
	}

	public Customer findOrMakeByEmail(String email)
	{
		Customer customer = this.findByEmail(email);
		if (customer == null) {
			customer = new Customer();
			customer.setEmail(email);
			this.saveCustomer(customer);
		}
		return customer;
	}
}
