package TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.GameState;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.enums.TieBreak;
import TennisMatchScoreboard.service.matchScoreCalcultaion.ScoreUpdater;

public class TieBreakScenario implements GameScenario {
    private final ScoreUpdater scoreUpdater = new ScoreUpdater();
    private final OngoingMatch ongoingMatch;

    public TieBreakScenario(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }



    @Override
    public void handle(MatchScore matchScore, Player player) {
        executeTieBreakScenario(ongoingMatch, matchScore, player);
    }


    private void executeTieBreakScenario(OngoingMatch ongoingMatch, MatchScore matchScore, Player player) {
        String currentPoints;
        String currentSets;

        if(player == Player.FIRST) {
            currentPoints = matchScore.getFirstPlayerPoints();
            currentSets = matchScore.getFirstPlayerSets();
        }else{
            currentPoints = matchScore.getSecondPlayerPoints();
            currentSets = matchScore.getSecondPlayerSets();
        }

        //TODO  не факт что этот код нужен && gameState == GameState.TIE_BREAK
        if(isTieBreakEnd(matchScore, player)){
            scoreUpdater.updatePlayerSets(matchScore, player, currentSets);
            ongoingMatch.setGameState(GameState.PLAYING);
            return;
        }

        TieBreak points = TieBreak.fromString(currentPoints);
        TieBreak nextPoint = points.nextPointsScore();
        scoreUpdater.updatePlayerTieBreakPoints(matchScore,player, nextPoint);
    }


    private boolean isTieBreakEnd(MatchScore matchScore, Player player){
        boolean firstPlayerWinTieBreak = matchScore.getFirstPlayerPoints().equals(TieBreak.SIX.toString());
        boolean secondPlayerWinTieBreak = matchScore.getSecondPlayerPoints().equals(TieBreak.SIX.toString());

        if(firstPlayerWinTieBreak && player == Player.FIRST){
            return true;
        }
        if(secondPlayerWinTieBreak && player == Player.SECOND){
            return true;
        }

        return false;
    }

}
