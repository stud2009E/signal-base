package pab.ta.handler.base.lib.exception;

public class RootSignalException extends RuntimeException {


    public RootSignalException(String message) {
        super(message);
    }

    public RootSignalException(String message, Throwable cause) {
        super(message, cause);
    }
}
