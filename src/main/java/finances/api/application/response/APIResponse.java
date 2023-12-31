package finances.api.application.response;
import finances.api.domain.exception.BusinessException;

import java.io.Serializable;
import java.util.List;

public abstract class APIResponse<T>  implements Serializable {
    protected int status;
    protected String message;

    public APIResponse() {}

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

    //TODO: testar se esse método vai ser capaz de devolver dados de erro ou de data das classes filhas
    public abstract List<T> content();
}
