package TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.GameState;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.service.matchScoreCalcultaion.GameAnalyzer;

public class ScenarioFactory {


    public static GameScenario createScenario(OngoingMatch ongoingMatch, MatchScore matchScore, GameState gameState) {
        GameAnalyzer gameAnalyzer = new GameAnalyzer(ongoingMatch);
        if(gameState == GameState.TIE_BREAK) {
            return new TieBreakScenario(ongoingMatch, gameAnalyzer);
        }
        if(isAdvantageScenario(matchScore)) {
            return new AdvantageScenario(ongoingMatch, gameAnalyzer);
        }
        return new RegularGameScenario(ongoingMatch, gameAnalyzer);
    }

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

