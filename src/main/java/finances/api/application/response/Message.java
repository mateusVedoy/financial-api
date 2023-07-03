package finances.api.application.response;

public class Message {
    private String message;
    private String stacktrace;

    public Message(String message, String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
    }

    public String getMessage() {
        return message;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    @Override
    public String toString() {
        return "Message: {" +
                "message:'" + message + '\'' +
                ", stacktrace:'" + stacktrace + '\'' +
                '}';
    }
}
