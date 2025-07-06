package TennisMatchScoreboard.dao;

import TennisMatchScoreboard.entity.Match;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.exceptions.DataBaseException;
import TennisMatchScoreboard.exceptions.NotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MatchDao extends BaseDao<Long, Match> {

    private final static MatchDao INSTANCE = new MatchDao();

    public static MatchDao getInstance() {
        return INSTANCE;
    }

    public MatchDao() {
        super(Match.class);
    }

    @Override
    public void update(Match match) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Player managedPlayer1 = managePlayer(session, match.getPlayer1());
            Player managedPlayer2 = managePlayer(session, match.getPlayer2());
            Player managedWinner = managePlayer(session, match.getWinner());
            match.setPlayer1(managedPlayer1);
            match.setPlayer2(managedPlayer2);
            match.setWinner(managedWinner);

            if (match.getId() == null) {
                session.persist(match);
            } else {
                session.merge(match);
            }

            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null){
                transaction.rollback();
            }
            throw new DataBaseException("Match persistence failed. Method: update()");
        } finally {
            session.close();
        }
    }

    private Player managePlayer(Session session, Player player) {
        if (player == null){
            throw new NotFoundException("Player cannot be null");
        }

        if (session.contains(player)) {
            return player;
        }

        if (player.getId() != null) {
            Player checkPlayer = session.find(Player.class, player.getId());
            if (checkPlayer != null){
                return checkPlayer;
            }
        }

        Player existing = session.createQuery(
                        "FROM Player WHERE name = :name", Player.class)
                .setParameter("name", player.getName())
                .uniqueResult();

        if (existing != null) {
            return existing;
        }
        try{
            session.persist(player);
            session.flush();
            return player;
        }catch (HibernateException e){
            throw new DataBaseException("Player persistence failed. Method: managePlayer()");
        }

    }
}
