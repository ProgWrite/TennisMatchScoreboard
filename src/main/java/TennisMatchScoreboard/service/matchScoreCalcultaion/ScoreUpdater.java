package TennisMatchScoreboard.service.matchScoreCalcultaion;


import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.enums.TieBreak;

public class ScoreUpdater {

    public ScoreUpdater(){

    }

    public void updatePlayerPoints(MatchScore matchScore, Player player, String currentPoints) {
        TennisScore points = TennisScore.fromString(currentPoints);
        TennisScore newPointsScore = points.nextPointsScore();

        if(player == Player.FIRST){
            matchScore.updateFirstPlayerPoints(newPointsScore);
        }else{
            matchScore.updateSecondPlayerPoints(newPointsScore);
        }
    }

    //TODO тоже сделай как аналогичные 3 метода
    public void updatePlayerTieBreakPoints(MatchScore matchScore, Player player, TieBreak newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerPoints(newScore);
        }else{
            matchScore.updateSecondPlayerPoints(newScore);
        }
    }

    public void updatePlayerGames(MatchScore matchScore, Player player, String currentGames) {
        TennisScore games = TennisScore.fromString(currentGames);
        TennisScore newGamesScore = games.nextGamesScore();

        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerGames(newGamesScore);
            updateScoreAfterGamePoint(matchScore);
        } else {
            matchScore.updateSecondPlayerGames(newGamesScore);
            updateScoreAfterGamePoint(matchScore);
        }
    }

    public void updatePlayerSets(MatchScore matchScore, Player player, String currentSets) {
        TennisScore sets = TennisScore.fromString(currentSets);
        TennisScore newSetScore = sets.nextSetsScore();

        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerSets(newSetScore);
            updateScoreAfterSetPoint(matchScore);
        } else {
            matchScore.updateSecondPlayerSets(newSetScore);
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
            updatePlayerSets(matchScore, player, currentSets);
        } else {
            String currentSets = matchScore.getSecondPlayerSets();
            updatePlayerSets(matchScore, player, currentSets);
        }
    }

}
