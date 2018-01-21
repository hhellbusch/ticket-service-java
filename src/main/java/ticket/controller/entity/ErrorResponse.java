package ticket.controller.entity;

public class ErrorResponse extends Response
{
	private String status = "ERROR";
	private String error;

	public void setError(String error)
	{
		this.error = error;
	}

	public Object getError()
	{
		return this.error;
	}

	public String getStatus()
	{
		return this.status;
	}
}