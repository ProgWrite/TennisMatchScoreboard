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
        TennisScore nextPointsScore = points.nextPointsScore();

        if(player == Player.FIRST){
            matchScore.updateFirstPlayerPoints(nextPointsScore);
        }else{
            matchScore.updateSecondPlayerPoints(nextPointsScore);
        }
    }

    //TODO тоже сделай как аналогичные 3 метода
    public void updatePlayerTieBreakPoints(MatchScore matchScore, Player player, String currentPoints) {
        TieBreak points = TieBreak.fromString(currentPoints);
        TieBreak nextPointsScore = points.nextPointsScore();

        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerPoints(nextPointsScore);
        }else{
            matchScore.updateSecondPlayerPoints(nextPointsScore);
        }
    }

    public void updatePlayerGames(MatchScore matchScore, Player player, String currentGames) {
        TennisScore games = TennisScore.fromString(currentGames);
        TennisScore nextGamesScore = games.nextGamesScore();

        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerGames(nextGamesScore);
            updateScoreAfterGamePoint(matchScore);
        } else {
            matchScore.updateSecondPlayerGames(nextGamesScore);
            updateScoreAfterGamePoint(matchScore);
        }
    }

    public void updatePlayerSets(MatchScore matchScore, Player player, String currentSets) {
        TennisScore sets = TennisScore.fromString(currentSets);
        TennisScore nextSetsScore = sets.nextSetsScore();

        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerSets(nextSetsScore);
            updateScoreAfterSetPoint(matchScore);
        } else {
            matchScore.updateSecondPlayerSets(nextSetsScore);
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
