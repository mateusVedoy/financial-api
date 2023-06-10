package finances.api.domain.port;

public interface IRepository<T> {
    T save(T entity);
}
