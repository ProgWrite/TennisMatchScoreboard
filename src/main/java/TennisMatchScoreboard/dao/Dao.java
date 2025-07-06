package TennisMatchScoreboard.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<K extends Serializable,E> {

    List<E> findAll();

    public void update(E entity);
}
