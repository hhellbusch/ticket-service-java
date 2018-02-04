package ticket.common.entity;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.Instant;

import org.junit.Test;

/**
 * Tests the functionality of the Venue entity
 *
 * @author Henry Hellbusch
 * @since 2018.02.01
 */
public class SeatHoldTest 
{

	private SeatHold subject;

	/**
	 * Sets up the test fixture. 
	 * (Called before every test case method.)
	 */
	@Before
	public void setUp() 
	{
		this.subject = new SeatHold();
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
	public void testGetIdIsInitiallyNull()
	{
		Integer id = this.subject.getId();
		assertNull("Id has not yet been set, should be NULL", id);
	}
	
	@Test
	public void testGetIdAfterSetId()
	{
		int id = 3456;
		this.subject.setId(id);
		int idFromSubject = this.subject.getId();
		assertEquals("id values should should equal each other", id, idFromSubject);
	}

	@Test
	public void testGetCustomerIdInitiallyNull()
	{
		Integer id = this.subject.getCustomerId();
		assertNull("customerId has not yet been set, should be NULL", id);
	}

	@Test
	public void testGetCustomerIdAfterSetCustomerId()
	{
		int id = 2345;
		this.subject.setCustomerId(id);
		int customerIdFromSubject = this.subject.getCustomerId();
		assertEquals("customerId values should should equal each other", id, customerIdFromSubject);
	}

	@Test
	public void testGetHoldTimeInitiallyNull()
	{
		Instant holdTime = this.subject.getHoldTime();
		assertNull("holdTime has not yet been set, should be NULL", holdTime);
	}

	@Test
	public void testGetHoldTimeAfterSetHoldTime()
	{
		Instant now = Instant.now();
		this.subject.setHoldTime(now);
		Instant holdTimeFromSubject = this.subject.getHoldTime();
		assertEquals("holdTime values should equal each other", now, holdTimeFromSubject);
	}

	@Test
	public void testGetBookingCodeInitiallyNull()
	{
		String bookingCode = this.subject.getBookingCode();
		assertNull("bookingCode has not yet been set, should be NULL", bookingCode);
	}

	@Test
	public void testGetBookingCodeAfterSetBookingCode()
	{
		String bookingCode = "hello world";
		this.subject.setBookingCode(bookingCode);
		String bookingCodeFromSubject = this.subject.getBookingCode();
		assertEquals("bookingCode values should equal each other", bookingCode, bookingCodeFromSubject);
	}

	@Test
	public void testGetBookingTimeInitiallyNull()
	{
		Instant bookingTime = this.subject.getBookingTime();
		assertNull("bookingCode has not yet been set, should be NULL", bookingTime);
	}

	@Test
	public void testGetBookingTimeAfterSetBookingTime()
	{
		Instant bookingTime = Instant.now();
		this.subject.setBookingTime(bookingTime);
		Instant bookingTimeFromSubject = this.subject.getBookingTime();
		assertEquals("bookingTime values should equal each other", bookingTime, bookingTimeFromSubject);
	}

	@Test
	public void testGetSeatCountInitiallyZero()
	{
		int seatCount = this.subject.getSeatCount();
		assertEquals("should be zero", 0, seatCount);
	}

	@Test
	public void testGetSeatCountAfterSetSeatCount()
	{
		int numSeats = 13;
		this.subject.setSeatCount(numSeats);
		int numSeatsFromSubject = this.subject.getSeatCount();
		assertEquals("num seats should be equal", numSeats, numSeatsFromSubject);
	}

}
