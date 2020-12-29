package datadump.exception;

public class ConfigParamException extends AppException {
	public ConfigParamException() {}

	public ConfigParamException(String message)
	{
		super(message);
	}

	public ConfigParamException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ConfigParamException(Throwable cause)
	{
		super(cause);
	}

	public ConfigParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
