package dxh.exceptions;


public class BusinessException extends Exception {
    private static final long serialVersionUID = -5320363526440721079L;
    private Object[] args;
    private Code code;
    private String resultTips;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Code code, Throwable cause, Object... args) {
        super(cause);
        this.args = args;
        this.code = code;
    }

    public BusinessException(Code code, Object... args) {
        this.code = code;
        this.args = args;
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(String resultTips, Code code, Object... args) {
        this.resultTips = resultTips;
        this.code = code;
        this.args = args;
    }

    public Code getCode() {
        return this.code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public String getMessage() {
        if(this.code == null) {
            return this.getCause() != null?this.getCause().getMessage():super.getMessage();
        } else {
            if(this.args == null || this.args.length < 1) {
                this.args = new Object[]{" "};
            }

            return this.getCause() != null?String.format(this.code.getMessage() + "" + this.getCause().getMessage(), this.args):String.format(this.code.getMessage(), this.args);
        }
    }

    public String getResultTips() {
        return this.resultTips;
    }

    public void setResultTips(String resultTips) {
        this.resultTips = resultTips;
    }
}
