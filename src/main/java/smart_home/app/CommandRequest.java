package smart_home.app;

public record CommandRequest(String deviceId, String action) implements Request {
    @Override public RequestType type() { return RequestType.COMMAND; }
}
