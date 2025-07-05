package TennisMatchScoreboard.service.matchScoreCalcultaion;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.GameState;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario.GameScenario;
import TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario.ScenarioFactory;


public class MatchScoreCalculationService {
    private final OngoingMatch ongoingMatch;
    private static final String FIRST_PLAYER_ACTION = "player1";

    public MatchScoreCalculationService(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public void gameScoreCalculation(String action){
        MatchScore matchScore = ongoingMatch.getMatchScore();
        Player player = action.equals(FIRST_PLAYER_ACTION) ? Player.FIRST : Player.SECOND;
        GameScenario scenario = ScenarioFactory.createScenario(ongoingMatch, matchScore, ongoingMatch.getGameState());
        scenario.handle(matchScore, player);

        if(isMatchEnd(ongoingMatch.getMatchScore())){
            ongoingMatch.setGameState(GameState.GAME_OVER);
        }
    }

    private boolean isMatchEnd(MatchScore matchScore) {
        return matchScore.getFirstPlayerSets().equals(TennisScore.TWO.toString())
                || matchScore.getSecondPlayerSets().equals(TennisScore.TWO.toString());
    }

}