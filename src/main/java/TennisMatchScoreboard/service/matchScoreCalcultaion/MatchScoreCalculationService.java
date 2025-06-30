package TennisMatchScoreboard.service.matchScoreCalcultaion;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario.GameScenario;
import TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario.ScenarioFactory;


public class MatchScoreCalculationService {
    private final OngoingMatch ongoingMatch;
    private static final String FIRST_PLAYER_ACTION = "player1";
    private static final String SECOND_PLAYER_ACTION = "player2";
    private final ScoreUpdater scoreUpdater = new ScoreUpdater();

    public MatchScoreCalculationService(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public void gameScoreCalculation2(String action){
        MatchScore matchScore = ongoingMatch.getMatchScore();
        Player player = action.equals(FIRST_PLAYER_ACTION) ? Player.FIRST : Player.SECOND;
        GameScenario scenario = ScenarioFactory.createScenario(ongoingMatch, matchScore, ongoingMatch.getGameState());
        scenario.handle(matchScore, player);
    }
}