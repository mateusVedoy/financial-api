package finances.api.application.response;
import finances.api.domain.exception.BusinessException;

import java.util.List;

public abstract class APIResponse {
    protected int status;
    protected String message;


    public APIResponse(int status, String message) {
        this.status = status;
        this.message = message;

    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
