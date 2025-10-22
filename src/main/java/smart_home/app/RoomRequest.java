package smart_home.app;

public record RoomRequest(String name, String action) implements Request {
    @Override public RequestType type() {
        return RequestType.ROOM;
    }
}