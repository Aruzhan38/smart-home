package smart_home.app.exception;

public class ExitRequestException extends RuntimeException {
    public ExitRequestException() {
        super("EXIT requested by user."); }
}
