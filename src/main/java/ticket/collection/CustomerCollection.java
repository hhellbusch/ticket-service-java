package ticket.collection;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;

import ticket.common.entity.Customer;

public class CustomerCollection
{
	private HashMap<String, Customer> customers;
	private final AtomicInteger counter = new AtomicInteger();

	public CustomerCollection()
	{
		this.customers = new HashMap<String, Customer>();
	}

	public void saveCustomer(Customer customer)
	{
		if (customer.getId() == 0) {
			this.customers.put(customer.getEmail(), customer);
		}

		//get the next id for a customer
		customer.setId(this.counter.incrementAndGet());
		//save the customer in the hashmap
		this.customers.put(customer.getEmail(), customer);
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
