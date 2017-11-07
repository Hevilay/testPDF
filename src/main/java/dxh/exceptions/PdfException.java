package dxh.exceptions;


/**
 * 报表异常基类
 *
 * @author huyu 8979@vetech.cn
 * @date 2017-08-21
 */
public class PdfException extends BusinessException {
	private static final long serialVersionUID = -1846268340121400139L;

	public PdfException(String message) {
		super(message);
	}

	public PdfException(Throwable cause) {
		super(cause);
	}

	public PdfException(Code code, Throwable cause, Object... args) {
		super(code, cause, args);
	}

	public PdfException(Code code, Object... args) {
		super(code, args);
	}

	public PdfException(String resultTips, Code code, Object... args) {
		super(resultTips, code, args);
	}
}
