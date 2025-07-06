package TennisMatchScoreboard.dao;

import TennisMatchScoreboard.exceptions.DataBaseException;
import TennisMatchScoreboard.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


@RequiredArgsConstructor
public class BaseDao<E> implements Dao<E> {

    private final Class<E> entityClass;
    protected final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();


    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();

            CriteriaQuery<E> criteria = session.getCriteriaBuilder().createQuery(entityClass);
            criteria.from(entityClass);
            List<E> catalog = session.createQuery(criteria).getResultList();
            session.getTransaction().commit();
            return catalog;

        }catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataBaseException(String.format(
                    "Database operation failed. Method: findAll(), Entity: %s, Error: %s",
                    entityClass.getName(),
                    e.getMessage()
            ));
        }
    }

    @Override
    public void update(E entity) {

    }
}
