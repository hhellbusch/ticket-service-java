package ticket.exception;

public class RequestedSeatsNotAvailableException extends Exception
{
	public RequestedSeatsNotAvailableException() { super(); } 
	public RequestedSeatsNotAvailableException(String s) { super(s); }
}
