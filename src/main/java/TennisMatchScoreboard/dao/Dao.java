package TennisMatchScoreboard.dao;

import java.util.List;

public interface Dao<E> {

    List<E> findAll();

   void update(E entity);
}
