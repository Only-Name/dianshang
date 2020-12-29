package datadump.exception;

/**
 * 帧头标志异常
 */
public class FrameHeadFlagException extends AppException {
	public FrameHeadFlagException() {}

	public FrameHeadFlagException(String message)
	{
		super(message);
	}

	public FrameHeadFlagException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public FrameHeadFlagException(Throwable cause)
	{
		super(cause);
	}

	public FrameHeadFlagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
