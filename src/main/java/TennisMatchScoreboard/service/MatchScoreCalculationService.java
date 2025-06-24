package TennisMatchScoreboard.service;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.Player;
import TennisMatchScoreboard.enums.TennisScore;

public class MatchScoreCalculationService {
    private final OngoingMatch ongoingMatch;
    private static final String FIRST_PLAYER_ACTION = "player1";
    private static final String SECOND_PLAYER_ACTION = "player2";

    public MatchScoreCalculationService(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public void gameScoreCalculation(String action) {
        MatchScore matchScore = ongoingMatch.getMatchScore();
        String firstPlayerPoints = matchScore.getFirstPlayerPoints();
        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String firstPlayerSets = matchScore.getFirstPlayerSets();

        String secondPlayerPoints = matchScore.getSecondPlayerPoints();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String secondPlayerSets = matchScore.getSecondPlayerSets();

        if (isAdvantageScenario(firstPlayerPoints, secondPlayerPoints)) {
            executeAdvantageLogic (matchScore, action, firstPlayerPoints, secondPlayerPoints,
                    firstPlayerGames, secondPlayerGames,
                    firstPlayerSets, secondPlayerSets);
        }

        else if (FIRST_PLAYER_ACTION.equals(action)) {
            updatePlayerScore(matchScore, Player.FIRST, firstPlayerPoints, firstPlayerGames, firstPlayerSets);
        }
        else if(SECOND_PLAYER_ACTION.equals(action)) {
            updatePlayerScore(matchScore, Player.SECOND, secondPlayerPoints, secondPlayerGames, secondPlayerSets);
        }

    }

    private void updatePlayerScore(MatchScore matchScore, Player player, String currentPoints, String currentGames, String currentSets){
        if (currentPoints.equals(TennisScore.FORTY.toString())) {
            if (currentGames.equals(TennisScore.FIVE.toString())) {
                TennisScore sets = TennisScore.fromString(currentSets);
                TennisScore newSetScore = calculateNextSetsScore(sets);
                updatePlayerSets(matchScore, player, newSetScore);
            } else {
                TennisScore games = TennisScore.fromString(currentGames);
                TennisScore newGamesScore = calculateNextGamesScore(games);
                updatePlayerGames(matchScore, player, newGamesScore);
            }
        } else {
            TennisScore points = TennisScore.fromString(currentPoints);
            TennisScore newPointsScore = calculateNextPointsScore(points);
            updatePlayerPoints(matchScore, player, newPointsScore);
        }
    }


    private void executeAdvantageLogic(MatchScore matchScore, String action, String firstPlayerPoints, String secondPlayerPoints,
                            String firstPlayerGames, String secondPlayerGames, String firstPlayerSets, String secondPlayerSets) {

            if ("player1".equals(action)) {
                if (firstPlayerPoints.equals(TennisScore.FORTY.toString()) &&
                        secondPlayerPoints.equals(TennisScore.ADVANTAGE.toString())) {
                    matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
                }
                else if (firstPlayerPoints.equals(TennisScore.FORTY.toString())) {
                    matchScore.updateFirstPlayerPoints(TennisScore.ADVANTAGE);
                }
                else if (firstPlayerPoints.equals(TennisScore.ADVANTAGE.toString())) {
                    if(firstPlayerGames.equals(TennisScore.FIVE.toString())) {
                        TennisScore sets = TennisScore.fromString(firstPlayerSets);
                        TennisScore newSetScore = calculateNextSetsScore(sets);
                        //TODO строку ниже можно вынести в метод ниже если я придумаю как избавить от зависимости игроков
                        matchScore.updateFirstPlayerSets(newSetScore);
                        updateScoreAfterSetPoint(matchScore);

                    }else{
                        updateScoreAfterGamePoint(matchScore);
                        TennisScore currentScore = TennisScore.fromString(firstPlayerGames);
                        TennisScore gamesScore = calculateNextGamesScore(currentScore);
                        matchScore.updateFirstPlayerGames(gamesScore);

                    }
                }
            }

            else if ("player2".equals(action)) {
                if (secondPlayerPoints.equals(TennisScore.FORTY.toString()) &&
                        firstPlayerPoints.equals(TennisScore.ADVANTAGE.toString())) {
                    matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
                }
                else if (secondPlayerPoints.equals(TennisScore.FORTY.toString())) {
                    matchScore.updateSecondPlayerPoints(TennisScore.ADVANTAGE);
                }
                else if (secondPlayerPoints.equals(TennisScore.ADVANTAGE.toString())) {
                    if(secondPlayerGames.equals(TennisScore.FIVE.toString())) {
                        TennisScore sets = TennisScore.fromString(secondPlayerSets);
                        TennisScore newSetScore = calculateNextSetsScore(sets);
                        //TODO строку ниже можно вынести в метод ниже если я придумаю как избавить от зависимости игроков
                        matchScore.updateSecondPlayerSets(newSetScore);
                        updateScoreAfterSetPoint(matchScore);

                    }else{
                        updateScoreAfterGamePoint(matchScore);
                        TennisScore currentScore = TennisScore.fromString(secondPlayerGames);
                        TennisScore gamesScore = calculateNextGamesScore(currentScore);
                        matchScore.updateSecondPlayerGames(gamesScore);

                    }
                }
            }

    }


    private void updatePlayerPoints(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerPoints(newScore);
        } else {
            matchScore.updateSecondPlayerPoints(newScore);
        }
    }

    private void updatePlayerGames(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerGames(newScore);
            updateScoreAfterGamePoint(matchScore);
        } else {
            matchScore.updateSecondPlayerGames(newScore);
            updateScoreAfterGamePoint(matchScore);
        }
    }

    private void updatePlayerSets(MatchScore matchScore, Player player, TennisScore newScore) {
        if (player == Player.FIRST) {
            matchScore.updateFirstPlayerSets(newScore);
            updateScoreAfterSetPoint(matchScore);
        } else {
            matchScore.updateSecondPlayerSets(newScore);
            updateScoreAfterSetPoint(matchScore);
        }
    }


    private TennisScore calculateNextPointsScore(TennisScore currentScore){
        TennisScore newPointsScore =

                switch (currentScore) {
                    case LOVE -> TennisScore.FIFTEEN;
                    case FIFTEEN -> TennisScore.THIRTY;
                    case THIRTY -> TennisScore.FORTY;
                    default -> throw new IllegalStateException("Unexpected value: " + currentScore);
                };
        return newPointsScore;
    }

    private TennisScore calculateNextGamesScore(TennisScore currentScore) {

        TennisScore newGamesScore =
                switch (currentScore){
                    case LOVE -> TennisScore.ONE;
                    case ONE -> TennisScore.TWO;
                    case TWO -> TennisScore.THREE;
                    case THREE -> TennisScore.FOUR;
                    case FOUR -> TennisScore.FIVE;
                    case FIVE -> TennisScore.SIX;
                    default -> throw new IllegalStateException("Unexpected value: " + currentScore);
                };
        return newGamesScore;
    }

    private TennisScore calculateNextSetsScore(TennisScore currentScore){

        TennisScore newSetsScore =
                switch (currentScore){
                    case LOVE -> TennisScore.ONE;
                    case ONE -> TennisScore.TWO;
                    default -> throw new IllegalStateException("Unexpected value: " + currentScore);
                };
        return newSetsScore;
    }


    private void updateScoreAfterGamePoint(MatchScore matchScore){
        matchScore.updateFirstPlayerPoints(TennisScore.LOVE);
        matchScore.updateSecondPlayerPoints(TennisScore.LOVE);
    }

    private void updateScoreAfterSetPoint(MatchScore matchScore){
        matchScore.updateFirstPlayerGames(TennisScore.LOVE);
        matchScore.updateSecondPlayerGames(TennisScore.LOVE);
        matchScore.updateFirstPlayerPoints(TennisScore.LOVE);
        matchScore.updateSecondPlayerPoints(TennisScore.LOVE);
    }


    private boolean isAdvantageScenario(String firstPlayerPoints, String secondPlayerPoints){
        boolean isDeuce = firstPlayerPoints.equals(TennisScore.FORTY.toString())
                        && secondPlayerPoints.equals(TennisScore.FORTY.toString());

        boolean isFirstPlayerAdvantage = firstPlayerPoints.equals(TennisScore.ADVANTAGE.toString())
                                        && secondPlayerPoints.equals(TennisScore.FORTY.toString());

        boolean isSecondPlayerAdvantage = firstPlayerPoints.equals(TennisScore.FORTY.toString())
                                        && secondPlayerPoints.equals(TennisScore.ADVANTAGE.toString());

        return isDeuce || isFirstPlayerAdvantage || isSecondPlayerAdvantage;
    }

}
