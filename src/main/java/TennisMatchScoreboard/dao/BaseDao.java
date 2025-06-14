package TennisMatchScoreboard.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//TODO если какие-то методы не понадобятся, тогда не используй их! Убери и в классах наследниках.
//TODO возможно нужно будет переименовать это в Repository!!!
@RequiredArgsConstructor
public class BaseDao<K extends Serializable, E> implements Dao<K,E> {


    private final Class<E> entityClass;
    private final EntityManager entityManager;

    @Override
    public E save(E entity) {

        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public List<E> findAll() {
        CriteriaQuery<E> criteria = entityManager.getCriteriaBuilder().createQuery(entityClass);
        criteria.from(entityClass);
        return entityManager.createQuery(criteria)
                .getResultList();
    }

    @Override
    public void delete(K id) {
       entityManager.remove(id);
       entityManager.flush();
    }
}
