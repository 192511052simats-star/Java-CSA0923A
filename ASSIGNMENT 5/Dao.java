import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    void insert(T obj) throws SQLException;
    T findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    void update(T obj) throws SQLException;
    void delete(K id) throws SQLException;
}