package TennisMatchScoreboard;


//TODO потом удали этот класс (будут норм тесты)

import TennisMatchScoreboard.dao.PlayerDao;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunnerTest {

    public static void main(String[] args) {

        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        PlayerDao playerDao = new PlayerDao(session);

        Player player = Player.builder()
                .name("Dmitry")
                .build();

        Player player2 = Player.builder()
                .name("Alfiya")
                .build();

        playerDao.save(player);
        playerDao.save(player2);

        System.out.println(playerDao.findAll());

        session.getTransaction().commit();

    }

}
