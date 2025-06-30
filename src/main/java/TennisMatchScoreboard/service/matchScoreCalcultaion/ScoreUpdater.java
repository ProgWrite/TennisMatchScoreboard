package TennisMatchScoreboard.service.matchScoreCalcultaion;


import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.enums.TieBreak;

public class ScoreUpdater {

    public ScoreUpdater(){

    }

    public void updatePlayerPoints(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerPoints(newScore);
        } else {
            matchScore.updateSecondPlayerPoints(newScore);
        }
    }

    public void updatePlayerTieBreakPoints(MatchScore matchScore, Player player, TieBreak newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerPoints(newScore);
        }else{
            matchScore.updateSecondPlayerPoints(newScore);
        }
    }

    public void updatePlayerGames(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerGames(newScore);
            updateScoreAfterGamePoint(matchScore);
        } else {
            matchScore.updateSecondPlayerGames(newScore);
            updateScoreAfterGamePoint(matchScore);
        }
    }

    public void updatePlayerSets(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerSets(newScore);
            updateScoreAfterSetPoint(matchScore);
        } else {
            matchScore.updateSecondPlayerSets(newScore);
            updateScoreAfterSetPoint(matchScore);
        }
    }

    public void updateScoreAfterGamePoint(MatchScore matchScore) {
        matchScore.updateFirstPlayerPoints(TennisScore.LOVE);
        matchScore.updateSecondPlayerPoints(TennisScore.LOVE);
    }

    public void updateScoreAfterSetPoint(MatchScore matchScore) {
        matchScore.updateFirstPlayerGames(TennisScore.LOVE);
        matchScore.updateSecondPlayerGames(TennisScore.LOVE);
        matchScore.updateFirstPlayerPoints(TennisScore.LOVE);
        matchScore.updateSecondPlayerPoints(TennisScore.LOVE);
    }

    public void updateScoreAfterAdditionalGame(MatchScore matchScore, Player player) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerGames(TennisScore.SIX);
           updateScoreAfterGamePoint(matchScore);
        } else {
            matchScore.updateSecondPlayerGames(TennisScore.SIX);
            updateScoreAfterGamePoint(matchScore);
        }
    }

    public void handleSixFiveGameScore(MatchScore matchScore, Player player) {
        if (player == Player.FIRST) {
            String currentSets = matchScore.getFirstPlayerSets();
            TennisScore sets = TennisScore.fromString(currentSets);
            TennisScore newSetScore = sets.nextSetsScore();
            updatePlayerSets(matchScore, player, newSetScore);
        } else {
            String currentSets = matchScore.getSecondPlayerSets();
            TennisScore sets = TennisScore.fromString(currentSets);
            TennisScore newSetScore = sets.nextSetsScore();
            updatePlayerSets(matchScore, player, newSetScore);
        }
    }

}
