package ticket.controller.entity;

public class CustomResponse extends Response
{
	private String status = "OK";
	private Object data;

	public void setData(Object data)
	{
		this.data = data;
	}

	public Object getData()
	{
		return this.data;
	}

	public String getStatus()
	{
		return this.status;
	}
}