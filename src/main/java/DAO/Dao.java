package DAO;

import java.util.List;
import java.util.Optional;

public interface Dao<K,E> {
    boolean update(E e);
    List<E> findAll();
    E findById(K id);
    E save(E e);
    boolean delete(K id);
}
