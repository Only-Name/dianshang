package datadump.exception;

public class ParamFormatException extends AppException {
	public ParamFormatException(Throwable cause)
	{
		super(cause);
	}

	public ParamFormatException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ParamFormatException(String message)
	{
		super(message);
	}

	public ParamFormatException() {}
}
