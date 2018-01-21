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
 */
public class SeatHold 
{
	private int id;

	private int customerId;
	private Instant holdTime;
	private int seatCount;

	private String bookingCode;
	private Instant bookingTime;

	// Getters and setters
	public int getId()
	{ 
		return this.id; 
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getCustomerId()
	{
		return this.customerId;
	}
	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}
	
	public Instant getHoldTime()
	{
		return this.holdTime;
	}
	public void setHoldTime(Instant holdTime)
	{
		this.holdTime = holdTime;
	}
	
	public String getBookingCode()
	{
		return this.bookingCode;
	}
	public void setBookingCode(String bookingCode)
	{
		this.bookingCode = bookingCode;
	}
	
	public Instant getBookingTime()
	{
		return this.bookingTime;
	}
	public void setBookingTime(Instant bookingTime)
	{
		this.bookingTime = bookingTime;
	}
	
	public int getSeatCount()
	{
		return this.seatCount;
	}
	public void setSeatCount(int count) 
	{
		this.seatCount = count;
	}

}
