package finances.api.domain.exception;

import finances.api.domain.entity.Entity;

import java.util.List;

public class BusinessValidationException extends Exception {
    private List<BusinessException> errors;

    public BusinessValidationException(List<BusinessException> errors) {
        this.errors = errors;
    }

    public List<BusinessException> getErrors() { return errors; }
}
