package finances.api.application.response;

import finances.api.domain.exception.BusinessException;
import finances.api.domain.port.IAPIResponse;

import java.util.ArrayList;
import java.util.List;

public class ResponseError extends APIResponse implements IAPIResponse{
    private List<Message> errors;

    public ResponseError(int status, String message, List<BusinessException> errors) {
        super(status, message);
        this.errors = buildMessagesFromBusinessException(errors);
    }

    public ResponseError(int status, String message, Exception ex) {
        super(status, message);
        errors = convertAnyExceptionToMessageList(ex);
    }

    @Override
    public IAPIResponse build() {
        return this;
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

    public List<Message> getErrors() {
        return errors;
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    private List<Message> convertAnyExceptionToMessageList(Exception ex) {
        return new ArrayList<>(List.of(
                new Message(ex.getMessage(), setStackTraceByErrorMessage(ex.getMessage()))
        ));
    }

    private String setStackTraceByErrorMessage(String message) {
        if(!isStringNullOrEmpty(message))
            return message.replace(" ", ".");
        return null;
    }

    private boolean isStringNullOrEmpty(String value) {
        return value == null || value.isEmpty() || value.isBlank();
    }
}
