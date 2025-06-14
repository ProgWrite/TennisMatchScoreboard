package TennisMatchScoreboard.dao;

import TennisMatchScoreboard.entity.Player;

import jakarta.persistence.EntityManager;

public class PlayerDao extends BaseDao<Long, Player> {

    public PlayerDao(EntityManager entityManager) {
        super(Player.class, entityManager);
    }

}
