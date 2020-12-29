package datadump.exception;

/**
 * 格式异常
 */
public class BinFormatException extends AppException {
	public BinFormatException() {
	}

	public BinFormatException(String message) {
		super(message);
	}

	public BinFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public BinFormatException(Throwable cause) {
		super(cause);
	}

	public BinFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
