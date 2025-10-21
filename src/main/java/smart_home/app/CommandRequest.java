package smart_home.app;

public record CommandRequest(String deviceId, String action, String voice, Boolean energy, Boolean online)
        implements Request {
    @Override public RequestType type() { return RequestType.COMMAND; }
}
