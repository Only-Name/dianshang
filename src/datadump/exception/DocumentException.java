package datadump.exception;

/**
 * 文档异常
 */
public class DocumentException extends AppException {
	public DocumentException() {
	}

	public DocumentException(String message) {
		super(message);
	}

	public DocumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentException(Throwable cause) {
		super(cause);
	}

	public DocumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
