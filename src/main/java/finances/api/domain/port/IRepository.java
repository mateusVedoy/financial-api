package finances.api.domain.port;

import java.util.List;

public interface IRepository<T> {
    T save(T entity);
    List<T> findAll();
    T findById(Long id);
    List<T> findByPeriod(String initialDate, String finalDate);
}
