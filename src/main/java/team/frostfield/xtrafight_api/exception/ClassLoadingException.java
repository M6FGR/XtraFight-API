package team.frostfield.xtrafight_api.exception;

public class ClassLoadingException extends RuntimeException {

    public ClassLoadingException(String message) {
        super(message);
    }

    public ClassLoadingException(String message, Throwable reason) {
        super(message, reason);
    }

    public ClassLoadingException() {
        super();
    }




}
