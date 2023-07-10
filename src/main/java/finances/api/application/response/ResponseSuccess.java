package finances.api.application.response;

import finances.api.domain.port.IAPIResponse;

import java.util.List;

public class ResponseSuccess<T> extends APIResponse implements IAPIResponse {
    private List<T> data;
    public ResponseSuccess(int status, String message) {
        super(status, message);
    }

    public ResponseSuccess(int status, String message, List<T> data) {
        super(status, message);
        this.data = data;
    }

    @Override
    public IAPIResponse build() {
        return this;
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
    public List getContent() {
        return data;
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
