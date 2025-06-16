package TennisMatchScoreboard.dao;

import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//TODO если какие-то методы не понадобятся, тогда не используй их! Убери и в классах наследниках.
//TODO возможно нужно будет переименовать это в Repository!!!

@RequiredArgsConstructor
public class BaseDao<K extends Serializable, E> implements Dao<K,E> {

    private final Class<E> entityClass;
    private final Session session;


    @Override
    public E create(E entity) {
        session.persist(entity);
        return entity;
    }

    @Override
    public void update(E entity) {
        session.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(session.find(entityClass, id));
    }

    @Override
    public List<E> findAll() {
        CriteriaQuery<E> criteria = session.getCriteriaBuilder().createQuery(entityClass);
        criteria.from(entityClass);
        return session.createQuery(criteria)
                .getResultList();
    }

    @Override
    public void delete(K id) {
        E entity = session.find(entityClass, id);
        if (entity != null) {
            session.remove(entity);
            session.flush();
        }
    }
}
