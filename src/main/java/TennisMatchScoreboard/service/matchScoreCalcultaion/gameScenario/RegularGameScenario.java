package TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.service.matchScoreCalcultaion.GameAnalyzer;
import TennisMatchScoreboard.service.matchScoreCalcultaion.ScoreUpdater;

public class RegularGameScenario  implements GameScenario {
    private final ScoreUpdater scoreUpdater = new ScoreUpdater();
    private final OngoingMatch ongoingMatch;
    private final GameAnalyzer gameAnalyzer;

    public RegularGameScenario(OngoingMatch ongoingMatch, GameAnalyzer gameAnalyzer) {
        this.ongoingMatch = ongoingMatch;
        this.gameAnalyzer = gameAnalyzer;
    }

    @Override
    public void handle(MatchScore matchScore, Player player) {
        updatePlayerScore(matchScore, player);
    }

    private void updatePlayerScore(MatchScore matchScore, Player player) {
         GameAnalyzer gameAnalyzer = new GameAnalyzer(ongoingMatch);
         String currentPoints;
         String currentGames;
         String currentSets;

        if(player == Player.FIRST){
           currentPoints = matchScore.getFirstPlayerPoints();
           currentGames = matchScore.getFirstPlayerGames();
           currentSets = matchScore.getFirstPlayerSets();
        }else{
            currentPoints = matchScore.getSecondPlayerPoints();
            currentGames = matchScore.getSecondPlayerGames();
            currentSets = matchScore.getSecondPlayerSets();
        }

        if (currentPoints.equals(TennisScore.FORTY.toString())) {
            if (gameAnalyzer.isAdditionalGame(matchScore)) {
                scoreUpdater.updateScoreAfterAdditionalGame(matchScore, player);
                return;
            }else if(gameAnalyzer.determineAdditionalGameEndOrStartTieBreak(matchScore,player)){
                return;
            }
            if (currentGames.equals(TennisScore.FIVE.toString())) {
                scoreUpdater.updatePlayerSets(matchScore, player, currentSets);
            } else {
                scoreUpdater.updatePlayerGames(matchScore, player, currentGames);
            }
        } else {
            scoreUpdater.updatePlayerPoints(matchScore, player, currentPoints);
        }
    }

}
