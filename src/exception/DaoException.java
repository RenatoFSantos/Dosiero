package exception;

public class DaoException extends Exception
{
	private static final long serialVersionUID = -4196249693238469346L;

	public DaoException(Exception e)
	{
		super(e);
	}

	public DaoException(String msg, Throwable e)
	{
		super(msg, e);
	}

	public DaoException(String msg)
	{
		super(msg);
	}
}
