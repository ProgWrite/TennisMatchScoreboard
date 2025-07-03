package TennisMatchScoreboard.service;


import TennisMatchScoreboard.dao.PlayerDao;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.util.HibernateUtil;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//TODO возможно тут будет валидация (как в Ролике у dmdev). Тогда уберется класс ValidationUtils.
//TODO возможно этот класс изменится

@RequiredArgsConstructor
public class PlayerService {

    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    //TODO может этот метод можно реализовать более изящно
    public Player create(Player player) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            Player existingPlayer = findPlayerByName(session, player.getName());

            if(existingPlayer != null) {
                session.getTransaction().commit();
                return existingPlayer;
            }

            PlayerDao playerDao = new PlayerDao(session);
            playerDao.create(player);
            session.flush();
            session.getTransaction().commit();
            return player;

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            session.close();
        }
    }

    // TODO почитай об этом подробнее!
    private Player findPlayerByName(Session session, String name) {
        return session.createQuery("FROM Player WHERE name = :name", Player.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .uniqueResult();
    }

}
