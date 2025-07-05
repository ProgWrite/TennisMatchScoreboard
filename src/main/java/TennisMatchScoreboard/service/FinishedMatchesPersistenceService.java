package TennisMatchScoreboard.service;

import TennisMatchScoreboard.dao.MatchDao;
import TennisMatchScoreboard.entity.Match;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
    private OngoingMatch ongoingMatch;
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();


    public FinishedMatchesPersistenceService(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public FinishedMatchesPersistenceService(){

    }

    // TODO мб код для Hibernate можно будет убрать чтобы избежать дублирования) И не создавать MatchDao в каждом методе, а делать так как было в 3 проекте (один экзмепляр)!
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

    //TODO мб тут будет Dto
    public List<Match> getFinishedMatches() {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            MatchDao matchDao = new MatchDao(session);

            List<Match> matches = matchDao.findAll();
            session.flush();
            session.getTransaction().commit();
            return matches;

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            session.close();
        }
    }

    public List<Match> findFinishedMatchesByPlayerName(String playerName) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            MatchDao matchDao = new MatchDao(session);

            List<Match> matches = matchDao.findAll();
            List<Match> matchesWithPlayer = findMatchesByPlayerName(matches, playerName);

            session.flush();
            session.getTransaction().commit();
            return matchesWithPlayer;

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            session.close();
        }
    }

    public Match saveFinishedMatch(OngoingMatch ongoingMatch) {
        Match savedMatch = Match.builder()
                .player1(ongoingMatch.getFirstPlayer())
                .player2(ongoingMatch.getSecondPlayer())
                .winner(determineWinner(ongoingMatch))
                .build();
        return savedMatch;
    }

    private Player determineWinner(OngoingMatch match){
        if(match.getMatchScore().getFirstPlayerSets().equals(TennisScore.TWO.toString())){
            return match.getFirstPlayer();
        }else{
            return match.getSecondPlayer();
        }
    }

    private List<Match> findMatchesByPlayerName(List<Match> matches, String playerName) {
        List<Match> matchesWithPlayerName = new ArrayList<Match>();
        for (Match match : matches) {
            if (match.getPlayer1().getName().equals(playerName)
                    || match.getPlayer2().getName().equals(playerName)) {
                matchesWithPlayerName.add(match);
            }
        }
        return matchesWithPlayerName;
    }

}
