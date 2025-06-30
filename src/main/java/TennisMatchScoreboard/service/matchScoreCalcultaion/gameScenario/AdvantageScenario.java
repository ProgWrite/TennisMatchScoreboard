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

    public AdvantageScenario(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    @Override
    public void handle(MatchScore matchScore, Player player) {
        executeAdvantageLogic(matchScore, player);
    }

    private void executeAdvantageLogic(MatchScore matchScore, Player player) {
        GameAnalyzer gameAnalyzer = new GameAnalyzer(ongoingMatch);

        boolean checkAdditionalGame = matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString()) &&
                matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString());
        //TODO потом тут проверка tie-break или нет (7:5 или 6:6). Скорее всего переименую переменную ниже boolean

        if (player == Player.FIRST) {
            if (matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString()) &&
                    matchScore.getSecondPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
            } else if (matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString())) {
                matchScore.updateFirstPlayerPoints(TennisScore.ADVANTAGE);
            } else if (matchScore.getFirstPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                if (checkAdditionalGame) {
                    scoreUpdater.updateScoreAfterAdditionalGame(matchScore, player);
                    return;
                }else if(gameAnalyzer.determineAdditionalGameEndOrStartTieBreak(matchScore,player)){
                    return;
                }
                if (matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString())) {
                    TennisScore sets = TennisScore.fromString(matchScore.getFirstPlayerSets());
                    TennisScore newSetScore = sets.nextSetsScore();
                    //TODO строку ниже можно вынести в метод ниже если я придумаю как избавить от зависимости игроков
                    matchScore.updateFirstPlayerSets(newSetScore);
                    scoreUpdater.updateScoreAfterSetPoint(matchScore);
                } else {
                    scoreUpdater.updateScoreAfterGamePoint(matchScore);
                    TennisScore currentGames = TennisScore.fromString(matchScore.getFirstPlayerGames());
                    TennisScore gamesScore = currentGames.nextGamesScore();
                    matchScore.updateFirstPlayerGames(gamesScore);
                }
            }
        } else if (player == Player.SECOND) {
            if (matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString()) &&
                    matchScore.getFirstPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
            } else if (matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString())) {
                matchScore.updateSecondPlayerPoints(TennisScore.ADVANTAGE);
            } else if (matchScore.getSecondPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                if (checkAdditionalGame) {
                    scoreUpdater.updateScoreAfterAdditionalGame(matchScore, player);
                    return;
                }else if(gameAnalyzer.determineAdditionalGameEndOrStartTieBreak(matchScore,player)){
                    return;
                }
                if (matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString())) {
                    TennisScore sets = TennisScore.fromString(matchScore.getSecondPlayerSets());
                    TennisScore newSetScore = sets.nextSetsScore();
                    //TODO строку ниже можно вынести в метод ниже если я придумаю как избавить от зависимости игроков
                    matchScore.updateSecondPlayerSets(newSetScore);
                    scoreUpdater.updateScoreAfterSetPoint(matchScore);
                } else {
                    scoreUpdater.updateScoreAfterGamePoint(matchScore);
                    TennisScore currentGames = TennisScore.fromString(matchScore.getSecondPlayerGames());
                    TennisScore gamesScore = currentGames.nextGamesScore();
                    matchScore.updateSecondPlayerGames(gamesScore);

                }
            }
        }
    }

}
