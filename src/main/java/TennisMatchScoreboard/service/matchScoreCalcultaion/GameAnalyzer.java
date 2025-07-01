package TennisMatchScoreboard.service.matchScoreCalcultaion;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;

public class GameAnalyzer {
    private final ScoreUpdater scoreUpdater = new ScoreUpdater();
    private final OngoingMatch ongoingMatch;


    public GameAnalyzer(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public boolean determineAdditionalGameEndOrStartTieBreak(MatchScore matchScore, Player player) {
        TieBreakInitializer tieBreakInitializer = new TieBreakInitializer(ongoingMatch);

        boolean checkFirstPlayer = matchScore.getFirstPlayerGames().equals(TennisScore.SIX.toString())
                && matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString())
                && matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString());

        boolean firstPlayerWinAdditionalGame = matchScore.getFirstPlayerGames().equals(TennisScore.SIX.toString())
                && matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString())
                && matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString());


        boolean checkSecondPlayer = matchScore.getSecondPlayerGames().equals(TennisScore.SIX.toString())
                && matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString())
                && matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString());

        boolean secondPlayerWinAdditionalGame = matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString())
                && matchScore.getSecondPlayerGames().equals(TennisScore.SIX.toString())
                && matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString());


        boolean checkSixSixGameScore = matchScore.getFirstPlayerGames().equals(TennisScore.SIX.toString()) &&
                matchScore.getSecondPlayerGames().equals(TennisScore.SIX.toString());

        boolean firstPlayerTieBreakAndAdvantageScenario =
                matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString()) &&
                        matchScore.getSecondPlayerGames().equals(TennisScore.SIX.toString()) &&
                        matchScore.getFirstPlayerPoints().equals(TennisScore.ADVANTAGE.toString()) &&
                        matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString());

        boolean secondPlayerTieBreakAndAdvantageScenario =
                matchScore.getFirstPlayerGames().equals(TennisScore.SIX.toString()) &&
                        matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString()) &&
                        matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString()) &&
                        matchScore.getSecondPlayerPoints().equals(TennisScore.ADVANTAGE.toString());


        if(checkFirstPlayer && player == Player.FIRST) {
            scoreUpdater.handleSixFiveGameScore(matchScore, player);
            return true;
        }else if(checkFirstPlayer && player == Player.SECOND) {
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }
      if(firstPlayerWinAdditionalGame && player == Player.FIRST) {
            scoreUpdater.handleSixFiveGameScore(matchScore, player);
            return true;
        }else if(secondPlayerWinAdditionalGame && player == Player.SECOND) {
            scoreUpdater.handleSixFiveGameScore(matchScore, player);
            return true;
        }

        if(checkSecondPlayer && player == Player.SECOND) {
            scoreUpdater.handleSixFiveGameScore(matchScore, player);
            return true;
        }else if(checkFirstPlayer && player == Player.FIRST) {
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }

        if (checkSecondPlayer && player == Player.FIRST){
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }

        if(checkSixSixGameScore){
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }

        if(firstPlayerTieBreakAndAdvantageScenario){
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }
        if(secondPlayerTieBreakAndAdvantageScenario){
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }
        return false;
    }

    public boolean isAdditionalGame(MatchScore matchScore) {
        return matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString()) &&
                matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString());
    }

}
