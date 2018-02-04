package ticket.common.entity;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the functionality of the Customer entity
 *
 * @author Henry Hellbusch
 * @since 2018.02.01
 */
public class CustomerTest 
{

	private Customer subject;

	@Before
	public void setUp()
	{
		this.subject = new Customer();
	}

	@After 
	public void tearDown()
	{
		this.subject = null;
	}

	@Test
	public void testGetIdIsInitiallyNull()
	{
		Integer id = this.subject.getId();
		assertNull("Id has not yet been set, should be NULL", id);
	}

	@Test
	public void testGetIdAfterSetId()
	{
		int id = 1234;
		this.subject.setId(id);
		int idFromSubject = this.subject.getId();
		assertEquals("id values should equal each other", id, idFromSubject);
	}

	@Test
	public void testGetEmailIsInitiallyNull()
	{
		String email = this.subject.getEmail();
		assertNull("email has not yet been set, should be NULL", email);
	}

	@Test
	public void testGetEmailAfterSetEmail()
	{
		String email = "HHELLBUSCH@GMAIL.COM";
		this.subject.setEmail(email);
		String emailFromSubject = this.subject.getEmail();
		assertEquals("email values should equal each other", email.toLowerCase(), emailFromSubject);
	}

}
