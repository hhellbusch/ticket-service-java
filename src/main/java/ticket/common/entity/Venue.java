package ticket.common.entity;

import ticket.exception.RequestedSeatsNotAvailableException;
import ticket.exception.UnableToFreeSeatsException;

/**
 * Describes the venue - e.g. number of rows and columns
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

	public int getTotalSeatCount()
	{
		return this.totalSeatCount;
	}

	public synchronized int getAvailSeatCount()
	{
		return this.getTotalSeatCount() - this.reservedCount - this.holdCount;
	}

	/**
	 * [findSeats description]
	 * @param  numSeats      [description]
	 * @param  customerEmail [description]
	 * @return               [description]
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

	public synchronized void reserveSeats(int numSeats)  {
		try {
			this.freeHeldSeats(numSeats);
		} catch (UnableToFreeSeatsException e) {
			
		}
		this.reservedCount += numSeats;
	}

	public synchronized void freeHeldSeats(int numSeats) throws UnableToFreeSeatsException {
		int totalIfFreed = this.getAvailSeatCount() + numSeats;
		if (totalIfFreed > this.getTotalSeatCount()) {
			String msg = "Unable to free " + numSeats 
				+ " seats.  There are not enough seats in the venue.";
			throw new UnableToFreeSeatsException(msg);
		}
		this.holdCount -= numSeats;
	}
}
