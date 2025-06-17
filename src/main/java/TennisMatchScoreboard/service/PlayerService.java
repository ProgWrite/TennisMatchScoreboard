package TennisMatchScoreboard.service;


import TennisMatchScoreboard.dao.PlayerDao;
import TennisMatchScoreboard.dto.PlayerDto;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.mapper.PlayerMapper;
import TennisMatchScoreboard.util.HibernateUtil;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//TODO возможно тут будет валидация (как в Ролике у dmdev). Тогда уберется класс ValidationUtils.
//TODO возможно этот класс изменится

@RequiredArgsConstructor
public class PlayerService {

    private final PlayerMapper playerMapper;
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public PlayerDto create(PlayerDto playerDto) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            Player player = playerMapper.mapToEntity(playerDto);

            if(isPlayerExists(session, player.getName())) {
                session.getTransaction().commit();
                return playerMapper.mapToDto(player);
            }

            PlayerDao playerDao = new PlayerDao(session);
            playerDao.create(player);
            session.flush();
            session.getTransaction().commit();
            return playerDto;

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            session.close();
        }
    }


    private boolean isPlayerExists(Session session, String name) {
        return session.createQuery("FROM Player WHERE name = :name", Player.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .uniqueResult() != null;
    }

}
