package TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.GameState;
import TennisMatchScoreboard.enums.TennisScore;

public class ScenarioFactory {


    public static GameScenario createScenario(OngoingMatch ongoingMatch, MatchScore matchScore, GameState gameState) {
        if(gameState == GameState.TIE_BREAK) {
            return new TieBreakScenario(ongoingMatch);
        }
        if(isAdvantageScenario(matchScore)) {
            return new AdvantageScenario(ongoingMatch);
        }
        return new RegularGameScenario(ongoingMatch);
    }


    //TODO возможно сделать GameState ADVANTAGE. Подумать об этом
    private static boolean isAdvantageScenario(MatchScore matchScore) {

        boolean isDeuce = matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString())
                && matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString());

        boolean isFirstPlayerAdvantage = matchScore.getFirstPlayerPoints().equals(TennisScore.ADVANTAGE.toString())
                && matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString());

        boolean isSecondPlayerAdvantage = matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString())
                && matchScore.getSecondPlayerPoints().equals(TennisScore.ADVANTAGE.toString());

        return isDeuce || isFirstPlayerAdvantage || isSecondPlayerAdvantage;
    }

}

