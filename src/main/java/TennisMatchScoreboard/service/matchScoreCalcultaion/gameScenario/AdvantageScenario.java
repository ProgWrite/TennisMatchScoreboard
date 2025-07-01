package TennisMatchScoreboard.service.matchScoreCalcultaion.gameScenario;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.service.matchScoreCalcultaion.GameAnalyzer;
import TennisMatchScoreboard.service.matchScoreCalcultaion.ScoreUpdater;

public class AdvantageScenario implements GameScenario {
    private final ScoreUpdater scoreUpdater = new ScoreUpdater();
    private final OngoingMatch ongoingMatch;
    private final GameAnalyzer gameAnalyzer;

    public AdvantageScenario(OngoingMatch ongoingMatch, GameAnalyzer gameAnalyzer) {
        this.ongoingMatch = ongoingMatch;
        this.gameAnalyzer = gameAnalyzer;
    }

    @Override
    public void handle(MatchScore matchScore, Player player) {
        executeAdvantageLogic(matchScore, player);
    }

    private void executeAdvantageLogic(MatchScore matchScore, Player player) {
        TennisScore currentPlayerScore = getCurrentPlayerPoints(matchScore, player);
        TennisScore opponentScore = getOpponentPoints(matchScore, player);

        if (currentPlayerScore == TennisScore.FORTY && opponentScore == TennisScore.ADVANTAGE) {
            updateOpponentPoints(matchScore, player, TennisScore.FORTY);
        } else if (currentPlayerScore == TennisScore.FORTY) {
            updatePlayerPoints(matchScore, player, TennisScore.ADVANTAGE);
        } else if (currentPlayerScore == TennisScore.ADVANTAGE) {
            handleGameWin(matchScore, player);
        }
    }

    private void handleGameWin(MatchScore matchScore, Player player) {
        if (gameAnalyzer.isAdditionalGame(matchScore)) {
            scoreUpdater.updateScoreAfterAdditionalGame(matchScore, player);
            return;
        }

        if (gameAnalyzer.determineAdditionalGameEndOrStartTieBreak(matchScore, player)) {
            return;
        }

        String playerGames = getPlayerGames(matchScore, player);
        if (TennisScore.fromString(playerGames) == TennisScore.FIVE) {
            scoreUpdater.updatePlayerSets(matchScore, player, getPlayerSets(matchScore, player));
        } else {
            scoreUpdater.updatePlayerGames(matchScore, player, playerGames);
        }
    }


    private TennisScore getCurrentPlayerPoints(MatchScore matchScore, Player player) {
        return TennisScore.fromString(
                player == Player.FIRST ? matchScore.getFirstPlayerPoints()
                                       : matchScore.getSecondPlayerPoints()
        );
    }

    private TennisScore getOpponentPoints(MatchScore matchScore, Player player) {
        return TennisScore.fromString(
                player == Player.FIRST
                        ? matchScore.getSecondPlayerPoints()
                        : matchScore.getFirstPlayerPoints()
        );
    }

    private String getPlayerGames(MatchScore matchScore, Player player) {
        return player == Player.FIRST
                ? matchScore.getFirstPlayerGames()
                : matchScore.getSecondPlayerGames();
    }

    private String getPlayerSets(MatchScore matchScore, Player player) {
        return player == Player.FIRST
                ? matchScore.getFirstPlayerSets()
                : matchScore.getSecondPlayerSets();
    }

    private void updatePlayerPoints(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerPoints(newScore);
        } else {
            matchScore.updateSecondPlayerPoints(newScore);
        }
    }

    private void updateOpponentPoints(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateSecondPlayerPoints(newScore);
        } else {
            matchScore.updateFirstPlayerPoints(newScore);
        }
    }

}




