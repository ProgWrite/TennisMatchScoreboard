package TennisMatchScoreboard.dao;

import TennisMatchScoreboard.entity.Match;
import org.hibernate.Session;

public class MatchDao extends BaseDao<Long, Match> {

    public MatchDao(Session session) {
        super(Match.class, session);
    }
}
