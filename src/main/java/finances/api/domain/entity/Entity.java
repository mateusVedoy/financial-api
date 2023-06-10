package finances.api.domain.entity;

import finances.api.domain.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private List<BusinessException> errors;

    public Entity() {
        errors = new ArrayList<>();
    }

    public boolean isValid() {
        return this.errors.size() == 0;
    }

    public List<BusinessException> getErrors() {
        return this.errors;
    }

    public void addError(String message, String property) {
        this.errors.add(new BusinessException(message, property));
    }

    protected abstract void validate();
}
