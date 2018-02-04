package ticket.collection;

import org.junit.*;
import static org.junit.Assert.*;

import ticket.common.entity.Customer;

public class CustomerCollectionTest
{

	private CustomerCollection subject;

	@Before
	public void setUp()
	{
		this.subject = new CustomerCollection();
	}

	@After 
	public void tearDown()
	{
		this.subject = null;
	}

	//test saveCustomer
	@Test
	public void testSaveCustomer()
	{
		//arrange
		//make a customer w/ an email
		Customer customer = new Customer();
		customer.setEmail("hhellbusch@gmail.com");
		//act
		//save the customer
		this.subject.saveCustomer(customer);
		//assert
		//make sure customer has an id now
		Integer id = customer.getId();
		assertNotNull("should not be null", id);
		if (id <= 0) {
			fail("Id should be greater than 0");
		}
	}

	@Test
	public void testSaveCustomerDoesNotChangeIdIfAlreadySet()
	{
		//arrange
		//make a customer w/ an email
		Customer customer = new Customer();
		customer.setEmail("hhellbusch@gmail.com");
		int id = 1234;
		customer.setId(id);
		//act
		//save the customer
		this.subject.saveCustomer(customer);
		//assert
		//make sure customer has an id now
		int idFromCustomer = customer.getId();
		assertEquals("id should not change", id, idFromCustomer);
	}

	//test findByEmail
	@Test
	public void testFindByEmailReturnsNullWhenNotFound()
	{
		Customer customer = this.subject.findByEmail("hhellbusch@gmail.com");
		assertNull("customer should not be found", customer);
	}

	@Test
	public void testFindByEmailFindsCustomer()
	{
		//arrange
		String email = "hhellbusch@gmail.com";
		Customer customer = new Customer();
		customer.setEmail(email);
		this.subject.saveCustomer(customer);
		//act
		Customer customerFromCollection = this.subject.findByEmail(email);
		//assert
		assertSame("should get same customer obj from collection", customer, customerFromCollection);
	}

	//test findOrMakeByEmail makes
	@Test 
	public void testFindOrMakeByEmailMakes()
	{
		String email = "hhellbusch@gmail.com";
		Customer doesNotExist = this.subject.findByEmail(email);
		assertNull("customer should not yet exist", doesNotExist);
		//use findOrMake
		Customer customer = this.subject.findOrMakeByEmail(email);
		assertNotNull("should not be null", customer);
		assertEquals("customer email should equal lookup", email, customer.getEmail());
		if (customer.getId() <= 0) {
			fail("customer should have a meaningful id");
		}
	}

	//test findOrMakeByEmail finds
	public void testFindOrMakeByEmailFinds()
	{
		String email = "hhellbusch@gmail.com";
		Customer customer = new Customer();
		customer.setEmail(email);
		this.subject.saveCustomer(customer);

		Customer customerFromCollection = this.subject.findOrMakeByEmail(email);
		assertNotNull("should not be null", customerFromCollection);
		assertSame("customers should be same", customer, customerFromCollection);
	}

}
