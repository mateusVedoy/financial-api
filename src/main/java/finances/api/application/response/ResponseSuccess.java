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
}
