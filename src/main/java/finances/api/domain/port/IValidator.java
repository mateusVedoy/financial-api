package finances.api.domain.port;

import java.util.List;

public interface IValidator<T> {
    boolean isValid(List<T> objects);
}
