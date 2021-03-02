package io.carsale.exception;

public class ApiException extends Exception {

    private static final long serialVersionUID = -2174637728997742359L;

    private int code;

    public ApiException(int code, String msg) {
        super(msg);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
