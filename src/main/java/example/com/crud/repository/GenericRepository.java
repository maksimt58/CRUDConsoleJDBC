package example.com.crud.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T,ID> {
    T getById(ID id);

    boolean delete(ID id);

    T update(T t);

    List<T> getAll();

    T save (T t);
}
