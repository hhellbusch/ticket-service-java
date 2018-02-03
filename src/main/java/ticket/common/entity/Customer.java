package ticket.common.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Entity that represents a customer.
 *
  * @author Henry Hellbusch <hhellbusch@gmail.com>
 * @since  2018.01.20
 */
public class Customer implements Serializable
{
	private Integer id;
	private String email;

	/**
	 * Returns the ID of the customer.  If the customer hasn't been
	 * assigned an ID, it will be NULL.
	 * @return ID of the customer
	 */
	public Integer getId()
	{
		return this.id;
	}

	/**
	 * Sets an ID for the customer
	 * @param id integer value to identify the customer by
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Returns the email of the customer. Will be NULL if 
	 * the email has not yet been set.
	 * @return customer's email 
	 */
	public String getEmail()
	{
		return this.email;
	}

	/**
	 * Sets the email address of the customer
	 * @param email the email address to set for the customer
	 */
	public void setEmail(String email)
	{
		//email addresses are case insensitive - lets cast to it lowercase
		this.email = email.toLowerCase();
	}
}
