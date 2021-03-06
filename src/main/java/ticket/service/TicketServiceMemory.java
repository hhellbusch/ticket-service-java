package ticket.service;

import java.util.UUID;
import java.time.Instant;
import java.time.Clock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ticket.common.entity.Customer;
import ticket.common.entity.SeatHold;
import ticket.common.entity.Venue;
import ticket.collection.CustomerCollection;
import ticket.exception.RequestedSeatsNotAvailableException;
import ticket.exception.UnableToFreeSeatsException;
import ticket.exception.UnableToReserveSeatsException;

/**
 * an implementation of the ticket service where 
 * all of the persistence is in the memory.
 *
 * if the runtime exits, data is lost
 *
 * Assumptions:
 *   only one venue
 *   
 * @author Henry Hellbusch <hhellbusch@gmail.com>
 * @since  2018.01.20
 */
public class TicketServiceMemory implements TicketService
{
	private static final Logger logger = LoggerFactory.getLogger(TicketServiceMemory.class);

	private CustomerCollection customerCollection;
	private Venue venue;
	private final int expireTimeSeconds;
	private Clock clock;
	private HashMap<Integer, SeatHold> heldSeats = new HashMap<Integer, SeatHold>();
	private HashMap<Integer, SeatHold> reservedSeats = new HashMap<Integer, SeatHold>();
	private final AtomicInteger counter = new AtomicInteger();

	public TicketServiceMemory(
		final CustomerCollection customerCollection
		, Venue venue
		, final int expireTimeSeconds
		, Clock clock
	) {
		this.customerCollection = customerCollection;
		this.expireTimeSeconds = expireTimeSeconds;
		this.venue = venue;
		this.clock = clock;
	}

	// Updates the internal clock of the service; added for easier unit testing
	public void setClock(Clock clock)
	{
		this.clock = clock;
	}

	@Override
	public int numSeatsAvailable()
	{
		this.removeExpiredSeats();
		return this.venue.getAvailSeatCount();
	}

	
	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		// get the availCount; this method has an important side effect of 
		// removing expired seats
		int availCount = this.numSeatsAvailable();
		SeatHold seatHold = null;
		try {
			seatHold = this.venue.findSeats(numSeats);
		} catch (RequestedSeatsNotAvailableException e) {
			String msg = "Seats that customer " + customerEmail
				+" requested are not available. Requested " + numSeats + " seats."
				+ " AvailCount: " + availCount;
			logger.debug(msg);
		}
		//if the seatHold is still null after trying to get a hold; return NULL
		if (seatHold == null) {
			return seatHold;
		}

		//get a customer
		Customer customer = this.customerCollection.findOrMakeByEmail(customerEmail);
		
		seatHold.setCustomerId(customer.getId());
		seatHold.setHoldTime(Instant.now(this.clock));
		seatHold.setId(this.counter.incrementAndGet());
		this.heldSeats.put(seatHold.getId(), seatHold);

		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail)
	{
		this.removeExpiredSeats();
		SeatHold seatHold = this.heldSeats.get(seatHoldId);
		if (seatHold == null) {
			return null;
		}

		// tell the venue that the seats are reserved now
		int seatCount = seatHold.getSeatCount();
		try {
			this.venue.reserveSeats(seatCount);
		} catch (UnableToReserveSeatsException e) {
			logger.debug(e.getMessage());
			return null;
		}

		String bookingCode = UUID.randomUUID().toString();
		seatHold.setBookingTime(Instant.now(this.clock));
		seatHold.setBookingCode(bookingCode);

		this.heldSeats.remove(seatHoldId);
		this.reservedSeats.put(seatHoldId, seatHold);

		return bookingCode;
	}

	private void removeExpiredSeats() {
		Instant now = Instant.now(this.clock);
		logger.debug("Checking for seats that are expired - now: " + now);
		logger.debug("Expire offset " + this.expireTimeSeconds);
		//done in separate loops just incase modifying the object being
		//iterated over is a bad practice (not sure about java)
		ArrayList<SeatHold> expiredSeatHolds = new ArrayList<SeatHold>();

		this.heldSeats.forEach((seatHoldId, seatHold) -> {
			Instant holdTime = seatHold.getHoldTime();
			Instant expireTime = holdTime.plusSeconds(this.expireTimeSeconds);
			int comparison = expireTime.compareTo(now);

			logger.debug("Hold Time   - " + holdTime);
			logger.debug("Expire time - " + expireTime);
			logger.debug("Comparison Result " + comparison);

			if (comparison < 0) {
				//add to delete list
				expiredSeatHolds.add(seatHold);
			}
		});
		logger.debug("Removing " + expiredSeatHolds.size());

		for(SeatHold seatHold : expiredSeatHolds) {
			int seatCount = seatHold.getSeatCount();
			try {
				this.venue.freeHeldSeats(seatCount);
			} catch (UnableToFreeSeatsException e) {
				// this should not occur; if it does - there's a bug / design flaw
				String msg = "FATAL - Asked to free " + seatCount 
					+ " UnableToFreeSeatsException occured. Exception message: " 
					+ e.getMessage();
				logger.error(msg);
			}
			this.heldSeats.remove(seatHold.getId());
		}
	}


}