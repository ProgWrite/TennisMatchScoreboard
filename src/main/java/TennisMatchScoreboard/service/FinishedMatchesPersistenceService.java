package TennisMatchScoreboard.service;

import TennisMatchScoreboard.dao.MatchDao;
import TennisMatchScoreboard.entity.Match;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
    private final OngoingMatch ongoingMatch;
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();


    public FinishedMatchesPersistenceService(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public void persistFinishedMatch(Match match) {
        UUID uuid = ongoingMatch.getUuid();
        ongoingMatchService.removeMatch(uuid);

        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            MatchDao matchDao = new MatchDao(session);
            matchDao.create(match);

            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            session.close();
        }
    }

}
