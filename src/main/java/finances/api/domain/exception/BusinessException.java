package finances.api.domain.exception;

public class BusinessException extends RuntimeException {
    private String property;

    public BusinessException(String message, String property){
        super(message);
        this.property = property;
    }
    public String getMessage() {
        return super.getMessage();
    }
    public String getProperty() {
        return property;
    }
}
