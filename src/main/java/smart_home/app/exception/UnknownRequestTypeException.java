package smart_home.app.exception;

public class UnknownRequestTypeException extends RuntimeException {
    public UnknownRequestTypeException(String type) { super(type); }
}

