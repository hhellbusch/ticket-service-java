package ticket.common.entity;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.Test;

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
	public void testGetRowCount() 
	{
		int result = this.subject.getRowCount();
		assertEquals("Row count should get set by constructor", this.rows, result);
	}

	@Test
	public void testGetColCount() 
	{
		int result = this.subject.getColCount();
		assertEquals("Col count should get set by constructor", this.cols, result);
	}

	@Test
	public void testGetTotalSeatCount() 
	{
		int result = this.subject.getTotalSeatCount();
		int expected = this.rows * this.cols;
		assertEquals("Total seat count should be equal to rows * cols", expected, result);
	}

	@Test 
	public void testGetAvailSeatCount()
	{
		// no reservations / holds put - count should equal total avail
		int expected = this.rows * this.cols;
		int result = this.subject.getAvailSeatCount();
		assertEquals("Total available seat count should be equal to rows * cols", expected, result);
	}

}
