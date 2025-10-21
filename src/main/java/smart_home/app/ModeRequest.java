package smart_home.app;

public record ModeRequest(String name) implements Request {
    @Override public RequestType type() { return RequestType.MODE; }
}
