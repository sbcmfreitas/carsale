package io.carsale.exception;

public class OptionQuantityRestrictionException extends ApiException {

    private static final long serialVersionUID = -8000903005649088468L;
    private int code;

    public OptionQuantityRestrictionException(int code, String msg) {
        super(code, msg);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
}
