package finances.api.domain.exception;

import java.util.List;

public class BusinessValidationError extends Exception {
    private List<BusinessException> errors;

    public BusinessValidationError(List<BusinessException> errors) {
        this.errors = errors;
    }

    public List<BusinessException> getErrors() { return errors; }
}
