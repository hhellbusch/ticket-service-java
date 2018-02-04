package ticket.common.entity;

import java.time.Instant;

/**
 * Entity to represent the seats that are on hold
 *
 * A seat hold belongs to one customer
 * A seat hold has a hold time and a booking time
 * Booking information is set on confirmation / reservation
 *   Booking Information - 
 *     bookingCode
 *     bookingTime
 *     
 * @author Henry Hellbusch <hhellbusch@gmail.com>
 * @since  2018.01.20
 */
public class SeatHold 
{
	private Integer id;

	private Integer customerId;
	private Instant holdTime;
	private int seatCount;

	private String bookingCode;
	private Instant bookingTime;

	/**
	 * Gets the ID of the seat hold. Will be NULL if an ID has not yet been 
	 * assigned
	 * @return the ID of the seat hold; null if not yet set
	 */
	public Integer getId()
	{ 
		return this.id; 
	}

	/**
	 * Sets the ID of the seat hold
	 * @param id The ID to set for the seat hold
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	/**
	 * Returns the customer ID for the seat hold; if no customer ID
	 * has been set this will return NULL
	 * @return The ID for the customer, if not yet set - will be null
	 */
	public Integer getCustomerId()
	{
		return this.customerId;
	}

	/**
	 * Sets the customer ID for the seat hold
	 * @param customerId the customer id to set on the seat hold
	 */
	public void setCustomerId(Integer customerId)
	{
		this.customerId = customerId;
	}
	
	/**
	 * Gets the time that the seat hold was put into place.
	 * If the hold time hasn't been set, NULL is returned
	 * @return the Instant that the seat hold was put into place; null
	 * if value has not been yet set
	 */
	public Instant getHoldTime()
	{
		return this.holdTime;
	}

	/**
	 * Sets the hold time for the seat hold
	 * @param holdTime when the seat hold was put into place
	 */
	public void setHoldTime(Instant holdTime)
	{
		this.holdTime = holdTime;
	}
	
	/**
	 * Returns the booking code for the seat hold.  The booking code
	 * should only exist after the seat hold has been finalized or confirmed
	 * @return the booking code for the seat hold, if the seat hold is not
	 * yet confirmed, then null
	 */
	public String getBookingCode()
	{
		return this.bookingCode;
	}

	/**
	 * Sets the booking code for the seat hold
	 * @param bookingCode the booking code to set
	 */
	public void setBookingCode(String bookingCode)
	{
		this.bookingCode = bookingCode;
	}
	
	/**
	 * Returns when the booking occured
	 * @return the booking time, if not yet booked, then null
	 */
	public Instant getBookingTime()
	{
		return this.bookingTime;
	}

	/**
	 * Sets the booking time
	 * @param bookingTime the booking time
	 */
	public void setBookingTime(Instant bookingTime)
	{
		this.bookingTime = bookingTime;
	}
	
	/**
	 * Gets the seat count for the seat hold
	 * @return the seat count 
	 */
	public int getSeatCount()
	{
		return this.seatCount;
	}

	/**
	 * Sets the seat count for the seat hold
	 * @param count the count of seats for the seat hold
	 */
	public void setSeatCount(int count) 
	{
		this.seatCount = count;
	}

}
