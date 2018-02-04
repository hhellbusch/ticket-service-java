package ticket.common.entity;

import ticket.exception.RequestedSeatsNotAvailableException;
import ticket.exception.UnableToFreeSeatsException;
import ticket.exception.UnableToReserveSeatsException;

/**
 * Describes the venue - e.g. number of rows and columns
 * and keeps track of the count of seats that are held and reserved
 * Does not keep track of whose seat is whose.  Movie theater style;
 * first come; gets first pick to seat at the venue
 * Assumes rectangular venue with rows and columns
 *
 * @author Henry Hellbusch
 * @since  2018.01.20
 */
public class Venue
{
	// the number of rows in the venue
	private int rows;
	// the number of cols in the venue
	private int cols;

	private int reservedCount = 0;
	private int holdCount = 0;

	private int totalSeatCount;

	public Venue(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.totalSeatCount = this.rows * this.cols;
	}

	/**
	 * Describes the number of seats that are present in the venue
	 * @return number of total seats in venue
	 */
	public int getTotalSeatCount()
	{
		return this.totalSeatCount;
	}

	/**
	 * Describes the available seat count in the venue. 
	 * @return the number of available seats int he venue
	 */
	public synchronized int getAvailSeatCount()
	{
		return this.getTotalSeatCount() - this.reservedCount - this.holdCount;
	}

	/**
	 * Places a hold on the requested count of seats.
	 * @param  numSeats the number of seats that are needed to be placed on hold
	 * @return A seat hold object describing the number of seats held
	 * @throws RequestedSeatsNotAvailableException Thrown if the number of available seats
	 * is less than the seats requested. So if 5 seats available and they request for
	 * 7 seats; exception will be thrown.
	 */
	public synchronized SeatHold findSeats(int numSeats) throws RequestedSeatsNotAvailableException
	{
		int availCount = this.getAvailSeatCount();
		if (availCount < numSeats) {
			// the amount of seats requested is more than avail - throw an exception
			String eMsg = "Unable to find seats - the amount requested ("
				+ numSeats + ") exceeds amount available ("+availCount+").";
			throw new RequestedSeatsNotAvailableException(eMsg);
		}
		
		this.holdCount = this.holdCount + numSeats;

		SeatHold seatHold = new SeatHold();
		seatHold.setSeatCount(numSeats);
		return seatHold;
	}

	/**
	 * Converts a the requested count of seat holds to a seat reservations.
	 * @param  numSeats the number of seats to place on reservation.
	 * @throws UnableToReserveSeatsException thrown if the number of seats cannot first be freed
	 * this suggests that the seats were never put on hold
	 */
	public synchronized void reserveSeats(int numSeats) throws UnableToReserveSeatsException {
		try {
			this.freeHeldSeats(numSeats);
		} catch (UnableToFreeSeatsException e) {
			String msg = "Unable to reserve seats because the seat holds were not "
				+ "free-able. Perhaps the seats weren't held in the first place? " + e.getMessage();
			throw new UnableToReserveSeatsException(msg);
		}
		this.reservedCount += numSeats;
	}

	/**
	 * Removes the requested number of seat holds from the venue
	 * @param  numSeats                   number of seat holds to remove
	 * @throws UnableToFreeSeatsException thrown if 
	 *   - freeing seats results in more seats than totally available.
	 *     suggests that seats were never put on hold in first place.
	 *   - somehow the total hold count would go negative; total hold count
	 *     should always be equal or greater than 0
	 */
	public synchronized void freeHeldSeats(int numSeats) throws UnableToFreeSeatsException {
		int totalIfFreed = this.getAvailSeatCount() + numSeats;
		int totalNotReserved = this.getTotalSeatCount() - this.reservedCount;
		if (totalIfFreed > totalNotReserved) {
			String msg = "Unable to free " + numSeats 
				+ " seats.  There are not enough seats in the venue.";
			throw new UnableToFreeSeatsException(msg);
		}
		if (this.holdCount - numSeats < 0) {
			// if there are no bugs; this shouldn't occur... just incase
			String msg = "Unable to free seats because it would cause the "
				+ "number of held seats to go negative.  Held seats should always be greater than 0.";
			throw new UnableToFreeSeatsException(msg);
		}
		this.holdCount -= numSeats;
	}
}
