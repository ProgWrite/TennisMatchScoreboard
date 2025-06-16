package TennisMatchScoreboard;


//TODO потом удали этот класс (будут норм тесты)

import TennisMatchScoreboard.dao.PlayerDao;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.mapper.PlayerMapper;
import TennisMatchScoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunnerTest {
    private final static SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final static PlayerMapper PLAYER_MAPPER = PlayerMapper.getInstance();



    public static void main(String[] args) {

        Session session = sessionFactory.getCurrentSession();

        PlayerDao playerDao = new PlayerDao(session);

        session.beginTransaction();

        Player player1 = Player.builder()
                .name("Player 1")
                .build();

        Player player2 = Player.builder()
                .name("Player 2")
                .build();

        Player player3 = Player.builder()
                .name("Player 2")
                .build();


        // Проверка и сохранение player1
        if (!isPlayerExists(session, player1.getName())) {
            playerDao.create(player1);
            System.out.println("Игрок " + player1.getName() + " создан!");
        }

        // Проверка и сохранение player2
        if (!isPlayerExists(session, player2.getName())) {
            playerDao.create(player2);
            System.out.println("Игрок " + player2.getName() + " создан!");
        }

        // Проверка player3 (дубликат player2)
        if (!isPlayerExists(session, player3.getName())) {
            playerDao.create(player3);
            System.out.println("Игрок " + player3.getName() + " создан!");
        } else {
            System.out.println("Игрок " + player3.getName() + " уже существует!");
        }

        session.getTransaction().commit();
    }



    private static boolean isPlayerExists(Session session, String name) {
        return session.createQuery("FROM Player WHERE name = :name", Player.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .uniqueResult() != null;
    }



}
