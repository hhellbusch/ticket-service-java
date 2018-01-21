package ticket.common.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Entity that represents a customer
 */
public class Customer implements Serializable
{
	private int id;
	private String email;
	// private ArrayList<SeatHold> seatHolds;


	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email)
	{
		//email addresses are case insensitive - lets cast to it lowercase
		this.email = email.toLowerCase();
	}
}
