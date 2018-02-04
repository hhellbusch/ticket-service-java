package ticket.common.entity;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.Test;
import ticket.exception.UnableToReserveSeatsException;
import ticket.exception.UnableToFreeSeatsException;
import ticket.exception.RequestedSeatsNotAvailableException;

/**
 * Tests the functionality of the Venue entity
 *
 * @author Henry Hellbusch
 * @since 2018.01.31
 */
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
		try {
			this.subject.findSeats(numToHold);
		} catch (Exception e) {
			fail("unexpected exception thrown message:" + e.getMessage());
		}
		int availSeats = this.subject.getAvailSeatCount();
		int expectedSeats = totalSeats - numToHold;

		String msg = "Total available seats should be equal to totalSeatCount - numHeld";
		assertEquals(msg, expectedSeats, availSeats);
	}

	@Test 
	public void testReserveSeatsThrowsExceptionWhenNoSeatsWereHeldFirst()
	{
		int totalSeats = this.rows * this.cols;
		int numToReserve = 3;
		try {
			this.subject.reserveSeats(numToReserve);
			fail ("expected exception message to be thrown because seats were not held prior");
		} catch (UnableToReserveSeatsException e) {
			// if code executes we received the expected exception pass
			assertTrue(true);
		}
	}

	@Test
	public void testGetAvailWithReservedSeats()
	{
		int totalSeats = this.rows * this.cols;
		int numToReserve = 4;
		int expectedSeats = totalSeats - numToReserve;

		//get a seat hold
		try {
			this.subject.findSeats(numToReserve);
			this.subject.reserveSeats(numToReserve);
		} catch (Exception e) {
			fail("unexpected exception thrown message:" + e.getMessage());
		}
		int availSeats = this.subject.getAvailSeatCount();

		String msg = "Total available seats should be equal to totalSeatCount - numHeld";
		assertEquals(msg, expectedSeats, availSeats);
	}

	@Test
	public void testGetAvailSeatCountWithHoldAndReservedSeats()
	{
		int totalSeats = this.rows * this.cols;
		int numToReserve = 4;
		int numToHold = 4;
		int expectedSeats = totalSeats - numToReserve - numToHold;

		//get a seat hold
		try {
			this.subject.findSeats(numToReserve);
			this.subject.reserveSeats(numToReserve);
			this.subject.findSeats(numToHold);
		} catch (Exception e) {
			fail("unexpected exception thrown message:" + e.getMessage());
		}
		int availSeats = this.subject.getAvailSeatCount();

		String msg = "Total available seats should be equal to totalSeatCount - numHeld";
		assertEquals(msg, expectedSeats, availSeats);
	}

	@Test
	public void testGetAvailSeatCountWithHeldAndFreedSeats()
	{
		int totalSeats = this.rows * this.cols;
		
		int numToHold = 4;
		int numToFree = 2;
		int expectedSeats = totalSeats - numToHold + numToFree;

		//get a seat hold
		try {
			this.subject.findSeats(numToHold);
			this.subject.freeHeldSeats(numToFree);
		} catch (Exception e) {
			fail("unexpected exception thrown message:" + e.getMessage());
		}
		int availSeats = this.subject.getAvailSeatCount();

		String msg = "Total available seats should be equal to totalSeatCount - numHeld";
		assertEquals(msg, expectedSeats, availSeats);
	}

	@Test
	public void testFreeHeldSeatsCreatesTooBigOfVenue()
	{
		// should not be able to free more seats than physically availble
		int numToFree = 4;
		try {
			this.subject.freeHeldSeats(numToFree);
			fail ("expected exception message to be thrown because seats were not held prior");
		} catch (UnableToFreeSeatsException e) {
			//exception was thrown due to no seats being held prior
			assertTrue(true);
		}
	}


	@Test
	public void testUnableToHoldMoreSeatsThanAvailable()
	{
		// should not be able to free more seats than physically availble
		int numToFind = this.rows * this.cols + 10;
		try {
			this.subject.findSeats(numToFind);
			fail ("expected exception message to be thrown because requested seats are not available");
		} catch (RequestedSeatsNotAvailableException e) {
			//exception was thrown due to no seats being held prior
			assertTrue(true);
		}
	}

}
