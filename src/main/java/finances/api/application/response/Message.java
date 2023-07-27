package finances.api.application.response;

import java.io.Serializable;

public class Message implements Serializable {
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
