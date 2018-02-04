package ticket.service;

import java.time.Clock;
import java.time.Instant;
import java.time.Duration;
import org.junit.*;
import org.junit.rules.*;
import org.junit.runners.model.FrameworkMethod;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ticket.collection.CustomerCollection;
import ticket.common.entity.Venue;
import ticket.common.entity.SeatHold;



public class TicketServiceMemoryTest 
{

	private TicketServiceMemory subject;
	private Clock clock;
	private int rows = 10;
	private int cols = 15;
	private int expireTimeSeconds = 120;


	/**
	 * Sets up the test fixture. 
	 * (Called before every test case method.)
	 */
	@Before
	public void setUp() 
	{
		CustomerCollection customerCollection = new CustomerCollection();
		Venue venue = new Venue(this.rows, this.cols);
		this.clock = Clock.systemDefaultZone();
		this.subject = new TicketServiceMemory(
			customerCollection
			, venue
			, this.expireTimeSeconds
			, this.clock
		);
	}

	/**
	 * Tears down the test fixture. 
	 * (Called after every test case method.)
	 */
	@After
	public void tearDown() 
	{
		this.subject = null;
		this.clock = null;
	}

	// findAndHoldSeats
	// if seats are avail, a seat hold
	@Test
	public void testFindAndHoldSeats()
	{
		int numSeats = 3;
		String email = "hhellbusch@gmail.com";
		SeatHold seatHold = this.subject.findAndHoldSeats(numSeats, email);
		assertNotNull("seat hold shouldn't be null", seatHold);
		Integer customerId = seatHold.getCustomerId();
		Integer id = seatHold.getId();
		Instant holdTime = seatHold.getHoldTime();
		assertNotNull("customer id should be set", customerId);
		assertNotNull("id should be set", id);
		assertNotNull("hold time should be set", holdTime);
	}
	
	// null if no seat avail
	@Test
	public void testFindAndHoldSeatsReturnsNullIfUnable()
	{
		int sizeOfVenue = this.rows * this.cols;
		String email = "hhellbusch@gmail.com";
		String secondEmail = "hhellbusch1@gmail.com";
		SeatHold allTheSeats = this.subject.findAndHoldSeats(sizeOfVenue, email);
		SeatHold shouldBeNull = this.subject.findAndHoldSeats(1, secondEmail);
		assertNotNull("should not be null", allTheSeats);
		assertNull("should be null", shouldBeNull);
	}

	@Test
	public void testFindAndHoldSeatsMaintainsCustomerId()
	{
		String email = "hhellbusch@gmail.com";
		SeatHold firstSeatHold = this.subject.findAndHoldSeats(3, email);
		SeatHold secondSeatHold = this.subject.findAndHoldSeats(5, email);
		Integer firstCustomerId = firstSeatHold.getCustomerId();
		Integer secondCustomerId = secondSeatHold.getCustomerId();
		Integer firstId = firstSeatHold.getId();
		Integer secondId = secondSeatHold.getId();
		assertEquals("same email, same customer, should be same customer id", firstCustomerId, secondCustomerId);
		assertNotEquals("different seat hold ids", firstId, secondId);
	}

	// numSeatsAvailable
	@Test
	public void testNumSeatsAvailable()
	{
		int numSeats = this.subject.numSeatsAvailable();
		int expectedNumSeats = this.rows * this.cols;
		assertEquals("all seats are available on init", expectedNumSeats, numSeats);
	}

	@Test
	public void testNumSeatsAvailableAfterSomeHeld()
	{
		int startNumSeats = this.subject.numSeatsAvailable();
		int numSeats = 3;
		String email = "hhellbusch@gmail.com";
		SeatHold seatHold = this.subject.findAndHoldSeats(numSeats, email);
		assertNotNull("seat hold shouldn't be null", seatHold);
		int expectedNumSeats = startNumSeats - numSeats;
		int numSeatsAvailAfterHold = this.subject.numSeatsAvailable();
		assertEquals("(start - held) should be the num avail now", expectedNumSeats, numSeatsAvailAfterHold);
	}

	@Test
	public void testNumSeatsAvailableAfterSomeReserved()
	{
		int startNumSeats = this.subject.numSeatsAvailable();
		int numSeats = 3;
		String email = "hhellbusch@gmail.com";
		SeatHold seatHold = this.subject.findAndHoldSeats(numSeats, email);
		assertNotNull("seat hold shouldn't be null", seatHold);
		int expectedNumSeats = startNumSeats - numSeats;
		int numSeatsAvailAfterHold = this.subject.numSeatsAvailable();
		assertEquals("(start - held) should be the num avail now", expectedNumSeats, numSeatsAvailAfterHold);
		this.subject.reserveSeats(seatHold.getId(), email);
		int numSeatsAvailAfterReserve = this.subject.numSeatsAvailable();
		assertEquals("(start - held) should be the num avail now", expectedNumSeats, numSeatsAvailAfterReserve);
	}


	// removes expired seats
	@Test
	public void testNumSeatsAvailableRemovesExpiredHolds()
	{
		int startNumSeats = this.subject.numSeatsAvailable();
		int numSeats = 3;
		String email = "hhellbusch@gmail.com";
		SeatHold seatHold = this.subject.findAndHoldSeats(numSeats, email);
		assertNotNull("seat hold shouldn't be null", seatHold);
		int expectedNumSeats = startNumSeats - numSeats;
		int numSeatsAvailAfterHold = this.subject.numSeatsAvailable();
		assertEquals("(start - held) should be the num avail now", expectedNumSeats, numSeatsAvailAfterHold);

		//make some time pass
		Duration offsetDuration = Duration.ofSeconds(this.expireTimeSeconds + 10);
		Clock futureClock = Clock.offset(this.clock, offsetDuration);
		this.subject.setClock(futureClock);
		int numSeatsAvailAfterTimePassage = this.subject.numSeatsAvailable();
		assertEquals("seats expired, should increase the seat count", startNumSeats, numSeatsAvailAfterTimePassage);
	}

	
	// reserveSeats
	// handles bad seatHoldId
	@Test 
	public void testReserveSeatsBadHoldId()
	{
		int startNumSeats = this.subject.numSeatsAvailable();
		String email = "hhellbusch@gmail.com";
		int madeUpId = 12345;
		String bookingCode = this.subject.reserveSeats(madeUpId, email);
		int endNumSeats = this.subject.numSeatsAvailable();

		assertNull("should be null, bad input", bookingCode);
		assertEquals("seat count should not change", startNumSeats, endNumSeats);
	}

	// sets booking code and time
	// returns a bookingcode 
	@Test
	public void testReserveSeatsGivesBookingCode()
	{
		int startNumSeats = this.subject.numSeatsAvailable();
		int numSeats = 3;
		String email = "hhellbusch@gmail.com";
		SeatHold seatHold = this.subject.findAndHoldSeats(numSeats, email);
		assertNotNull("seat hold shouldn't be null", seatHold);

		String bookingCode = this.subject.reserveSeats(seatHold.getId(), email);
		assertNotNull("shouldn't be null", bookingCode);
	}
}
