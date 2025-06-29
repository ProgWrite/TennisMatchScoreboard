package TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario;


import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.Player;

public interface GameScenario {
    void handle(MatchScore matchScore, Player player);
}
