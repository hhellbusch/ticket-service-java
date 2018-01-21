package ticket.service;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TicketServiceMemoryTest 
{

	private TicketServiceMemory subject;


	/**
	 * Sets up the test fixture. 
	 * (Called before every test case method.)
	 */
	@Before
	public void setUp() 
	{
		//create mock ticketCollection
		//create mock customerCollection
		// this.subject = new TicketServiceMemory();
	}

	/**
	 * Tears down the test fixture. 
	 * (Called after every test case method.)
	 */
	@After
	public void tearDown() 
	{
		this.subject = null;
	}

}
