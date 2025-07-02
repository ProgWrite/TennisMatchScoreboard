package TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.GameState;
import TennisMatchScoreboard.enums.Player;

public class GameOverScenario implements GameScenario {
   private final OngoingMatch ongoingMatch;

    public GameOverScenario(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    @Override
    public void handle(MatchScore matchScore, Player player) {
        ongoingMatch.setGameState(GameState.GAME_OVER);
    }
}
