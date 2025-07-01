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

    public boolean isAdditionalGame(MatchScore matchScore) {
        return matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString()) &&
                matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString());
    }

    public boolean determineAdditionalGameEndOrStartTieBreak(MatchScore matchScore, Player player) {
        if(isTieBreak(matchScore, player)){
            return true;
        }
        return isSixFiveGameScore(matchScore, player);
    }

    private boolean isTieBreak(MatchScore matchScore, Player player){
        TieBreakInitializer tieBreakInitializer = new TieBreakInitializer(ongoingMatch);
        TennisScore firstPlayerGames = getPlayerGames(matchScore, Player.FIRST);
        TennisScore secondPlayerGames = getPlayerGames(matchScore, Player.SECOND);
        TennisScore firstPlayerPoints = getPlayerPoints(matchScore, Player.FIRST);
        TennisScore secondPlayerPoints = getPlayerPoints(matchScore, Player.SECOND);

        if (firstPlayerGames == TennisScore.SIX && secondPlayerGames == TennisScore.SIX) {
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }
        if (player == Player.FIRST && firstPlayerGames == TennisScore.FIVE && secondPlayerGames == TennisScore.SIX
                && firstPlayerPoints == TennisScore.ADVANTAGE && secondPlayerPoints == TennisScore.FORTY) {
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }

        if (player == Player.SECOND && secondPlayerGames == TennisScore.FIVE && firstPlayerGames == TennisScore.SIX
                && secondPlayerPoints == TennisScore.ADVANTAGE && firstPlayerPoints == TennisScore.FORTY) {
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }

        if (player == Player.SECOND && firstPlayerGames == TennisScore.SIX && secondPlayerGames == TennisScore.FIVE
                && secondPlayerPoints == TennisScore.FORTY) {
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }

        if (player == Player.FIRST && secondPlayerGames == TennisScore.SIX && firstPlayerGames == TennisScore.FIVE
                && firstPlayerPoints == TennisScore.FORTY) {
            tieBreakInitializer.startTieBreakRules(matchScore);
            return true;
        }
        return false;
    }

    private boolean isSixFiveGameScore(MatchScore matchScore, Player player) {
        TennisScore firstPlayerGames = getPlayerGames(matchScore, Player.FIRST);
        TennisScore secondPlayerGames = getPlayerGames(matchScore, Player.SECOND);
        TennisScore firstPlayerPoints = getPlayerPoints(matchScore, Player.FIRST);
        TennisScore secondPlayerPoints = getPlayerPoints(matchScore, Player.SECOND);

        if (player == Player.FIRST && firstPlayerGames == TennisScore.SIX && secondPlayerGames == TennisScore.FIVE) {
            if (firstPlayerPoints == TennisScore.FORTY || firstPlayerPoints == TennisScore.ADVANTAGE) {
                scoreUpdater.handleSixFiveGameScore(matchScore, player);
                return true;
            }
        }

        if (player == Player.SECOND && secondPlayerGames == TennisScore.SIX && firstPlayerGames == TennisScore.FIVE) {
            if (secondPlayerPoints == TennisScore.FORTY || secondPlayerPoints == TennisScore.ADVANTAGE) {
                scoreUpdater.handleSixFiveGameScore(matchScore, player);
                return true;
            }
        }
        return false;
    }

    private TennisScore getPlayerPoints(MatchScore matchScore, Player player) {
        return TennisScore.fromString(
                player == Player.FIRST
                        ? matchScore.getFirstPlayerPoints()
                        : matchScore.getSecondPlayerPoints()
        );
    }

    private TennisScore getPlayerGames(MatchScore matchScore, Player player) {
        return TennisScore.fromString(
                player == Player.FIRST
                        ? matchScore.getFirstPlayerGames()
                        : matchScore.getSecondPlayerGames()
        );
    }

}




