package ticket.common.entity;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class VenueTest 
{

	private Venue subject;
	private final int rows = 5;
	private final int cols = 10;


	/**
	 * Sets up the test fixture. 
	 * (Called before every test case method.)
	 */
	@Before
	public void setUp() 
	{
		this.subject = new Venue(this.rows, this.cols);
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

	@Test
	public void testGetTotalSeatCount() 
	{
		int result = this.subject.getTotalSeatCount();
		int expected = this.rows * this.cols;
		String msg = "Total seat count should be equal to rows * cols";
		assertEquals(msg, expected, result);
	}

	@Test 
	public void testGetAvailSeatCountWithNoHolds()
	{
		// no reservations / holds put - count should equal total avail
		int expected = this.rows * this.cols;
		int result = this.subject.getAvailSeatCount();
		String msg = "Total available seat count should be equal to rows * cols";
		assertEquals(msg, expected, result);
	}

	@Test
	public void testGetAvailSeatCountWithHeldSeats()
	{
		int totalSeats = this.rows * this.cols;
		int numToHold = 10;
		this.subject.findSeats(numToHold);
		int availSeats = this.getAvailSeatCount();
		int expectedSeats = totalSeats - numToHold;

		String msg = "Total available seats should be equal to totalSeatCount - numHeld";
		assertEquals(msg, expectedSeats, availSeats);
	}

	@Test 
	public void testGetAvailSeatCountWithReservedSeats()
	{
		int totalSeats = this.rows * this.cols;
		int numToReserve = 3;
		this.subject.reserveSeats(numToHold);
		int availSeats = this.getAvailSeatCount();
		int expectedSeats = totalSeats - numToReserve;

		String msg = "Total available seats should be equal to totalSeatCount - numHeld";
		assertEquals(msg, expectedSeats, availSeats);
	}

	// @Test
	// public void testGetAvailSeatCountWithHoldAndReservedSeats()
	// {
		
	// }

	//TODO test for findSeats throws exception
	//TODO test for freeHeldSeats throws exception

}
