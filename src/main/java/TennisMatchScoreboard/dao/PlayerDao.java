package TennisMatchScoreboard.dao;

import TennisMatchScoreboard.entity.Player;
import org.hibernate.Session;


public class PlayerDao extends BaseDao<Long, Player> {

    public PlayerDao(Session session) {
        super(Player.class, session);
    }

}
