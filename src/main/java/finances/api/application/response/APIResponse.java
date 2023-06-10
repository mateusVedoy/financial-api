package finances.api.application.response;
import finances.api.domain.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class APIResponse {
    private int status;
    private String message;

    private List<Message> errors;



    public APIResponse(int status, String message, List<BusinessException> errors) {
        this.status = status;
        this.message = message;
        this.errors = buildMessagesFromBusinessException(errors);
    }

    private List<Message> buildMessagesFromBusinessException(List<BusinessException> errors) {

        if(errors == null)
            return null;

        List<Message> messages = new ArrayList<>();

        errors.forEach(error -> {
            messages.add(new Message(error.getMessage(), error.getProperty()));
        });

        return messages;
    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    public List<Message> getErrors() {
        return errors;
    }
}
