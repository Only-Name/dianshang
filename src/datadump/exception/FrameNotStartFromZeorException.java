package datadump.exception;

/**
 * 帧不是从0开始异常
 */
public class FrameNotStartFromZeorException extends AppException {
	public FrameNotStartFromZeorException() {}

	public FrameNotStartFromZeorException(String message)
	{
		super(message);
	}

	public FrameNotStartFromZeorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public FrameNotStartFromZeorException(Throwable cause)
	{
		super(cause);
	}

	public FrameNotStartFromZeorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
