package finances.api.domain.port;

import java.time.LocalDate;
import java.util.List;

public interface IRepository<T> {
    T save(T entity);
    List<T> findAll();
    T findById(Long id);
    List<T> findByPeriod(LocalDate initialDate, LocalDate finalDate);
}
