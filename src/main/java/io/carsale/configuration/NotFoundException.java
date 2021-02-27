package io.carsale.configuration;

public class NotFoundException extends ApiException {

    private static final long serialVersionUID = -8000903005649088467L;
    private int code;

    public NotFoundException(int code, String msg) {
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
