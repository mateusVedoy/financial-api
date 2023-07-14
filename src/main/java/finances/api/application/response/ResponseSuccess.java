package finances.api.application.response;

import java.util.List;

public class ResponseSuccess<T> extends APIResponse {
    private List<T> data;

    public ResponseSuccess(int status, String message) {
        super(status, message);
    }

    public ResponseSuccess(int status, String message, List<T> data) {
        super(status, message);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List content() {
        return this.data;
    }

    private String getObjectsToStringFromData() {
        if(hasData()){
            StringBuilder builder = new StringBuilder();
            builder.append("[ ");
            this.data.forEach(value -> {
                builder.append(value.toString());
                builder.append(" ");
            });
            builder.append("]");
            return builder.toString();
        }
        return null;
    }
    private boolean hasData() {
        return this.data != null && this.data.size() > 0;
    }
    @Override
    public String toString() {
        return "ResponseSuccess: {" +
                "data:" + getObjectsToStringFromData() +
                ", status:" + status +
                ", message:'" + message + '\'' +
                '}';
    }
}
