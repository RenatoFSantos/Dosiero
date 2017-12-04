package exception;

public class ControllerException extends Exception
{

	private static final long serialVersionUID = -2380214240202995834L;

	public ControllerException(Exception e)
	{
		super(e);
	}

	public ControllerException(String msg, Throwable e)
	{
		super(msg, e);
	}

	public ControllerException(String msg)
	{
		super(msg);
	}
}
