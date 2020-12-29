package datadump.exception;

/**
 * 小文件大小异常
 */
public class SmallFileSizeException extends AppException {
	public SmallFileSizeException() {}

	public SmallFileSizeException(String message)
	{
		super(message);
	}

	public SmallFileSizeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SmallFileSizeException(Throwable cause)
	{
		super(cause);
	}

	public SmallFileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
