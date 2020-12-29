package datadump.exception;

import java.io.IOException;

/**
 * 文件写入异常
 */
public class WriterFredFileException extends AppException {
	public WriterFredFileException(IOException ex)
	{
		super(ex);
	}
}
