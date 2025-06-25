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

        if (isAdvantageScenario(matchScore.getFirstPlayerPoints(), matchScore.getSecondPlayerPoints())) {
            if(FIRST_PLAYER_ACTION.equals(action)) {
                executeAdvantageLogic (matchScore, action, Player.FIRST);
            }else{
                executeAdvantageLogic (matchScore, action, Player.SECOND);
            }
        }
        else if (FIRST_PLAYER_ACTION.equals(action)) {
            updatePlayerScore(matchScore, Player.FIRST, matchScore.getFirstPlayerPoints(),
                    matchScore.getFirstPlayerGames(), matchScore.getFirstPlayerSets());
        }
        else if(SECOND_PLAYER_ACTION.equals(action)) {
            updatePlayerScore(matchScore, Player.SECOND, matchScore.getSecondPlayerPoints(),
                    matchScore.getSecondPlayerGames(), matchScore.getSecondPlayerSets());
        }

    }

    private void updatePlayerScore(MatchScore matchScore, Player player, String currentPoints, String currentGames, String currentSets) {
        boolean checkAdditionalGame = matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString()) &&
                matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString());
        //TODO потом тут проверка tie-break или нет (7:5 или 6:6). Скорее всего переименую переменную ниже boolean

        boolean isTieBreak = isAdditionalGameEnd(matchScore);

        if (currentPoints.equals(TennisScore.FORTY.toString())) {
            if (checkAdditionalGame) {
                playAdditionalGame(matchScore, player);
            }
            else if(isTieBreak) {
                handleSixFiveGameScore(matchScore, player);
            }
            else if (currentGames.equals(TennisScore.FIVE.toString())) {
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


    private void executeAdvantageLogic(MatchScore matchScore, String action, Player player) {
        boolean checkAdditionalGame = matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString()) &&
                matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString());
        //TODO потом тут проверка tie-break или нет (7:5 или 6:6). Скорее всего переименую переменную ниже boolean

        boolean isTieBreak = isAdditionalGameEnd(matchScore);

            if ("player1".equals(action)) {
                if (matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString()) &&
                        matchScore.getSecondPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                    matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
                }
                else if (matchScore.getFirstPlayerPoints().equals(TennisScore.FORTY.toString())) {
                    matchScore.updateFirstPlayerPoints(TennisScore.ADVANTAGE);
                }
                else if (matchScore.getFirstPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                    if (checkAdditionalGame) {
                        playAdditionalGame(matchScore, player);
                    }
                    else if(isTieBreak) {
                        handleSixFiveGameScore(matchScore, player);
                    }
                    else if(matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString())) {
                        TennisScore sets = TennisScore.fromString(matchScore.getFirstPlayerSets());
                        TennisScore newSetScore = calculateNextSetsScore(sets);
                        //TODO строку ниже можно вынести в метод ниже если я придумаю как избавить от зависимости игроков
                        matchScore.updateFirstPlayerSets(newSetScore);
                        updateScoreAfterSetPoint(matchScore);

                    }
                    else{
                        updateScoreAfterGamePoint(matchScore);
                        TennisScore currentScore = TennisScore.fromString(matchScore.getFirstPlayerGames());
                        TennisScore gamesScore = calculateNextGamesScore(currentScore);
                        matchScore.updateFirstPlayerGames(gamesScore);

                    }
                }
            }

            else if ("player2".equals(action)) {
                if (matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString()) &&
                        matchScore.getFirstPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                    matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
                }
                else if (matchScore.getSecondPlayerPoints().equals(TennisScore.FORTY.toString())) {
                    matchScore.updateSecondPlayerPoints(TennisScore.ADVANTAGE);
                }
                else if (matchScore.getSecondPlayerPoints().equals(TennisScore.ADVANTAGE.toString())) {
                    if (checkAdditionalGame) {
                        playAdditionalGame(matchScore, player);
                    }
                    else if(isTieBreak) {
                        handleSixFiveGameScore(matchScore, player);
                    }
                    else if(matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString())) {
                        TennisScore sets = TennisScore.fromString(matchScore.getSecondPlayerSets());
                        TennisScore newSetScore = calculateNextSetsScore(sets);
                        //TODO строку ниже можно вынести в метод ниже если я придумаю как избавить от зависимости игроков
                        matchScore.updateSecondPlayerSets(newSetScore);
                        updateScoreAfterSetPoint(matchScore);

                    }
                    else{
                        updateScoreAfterGamePoint(matchScore);
                        TennisScore currentScore = TennisScore.fromString(matchScore.getSecondPlayerGames());
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

    private void playAdditionalGame(MatchScore matchScore, Player player){
        if(player == Player.FIRST) {
            matchScore.updateFirstPlayerGames(TennisScore.SIX);
            updateScoreAfterGamePoint(matchScore);
        }else{
            matchScore.updateSecondPlayerGames(TennisScore.SIX);
            updateScoreAfterGamePoint(matchScore);
        }
    }

    private boolean isAdditionalGameEnd(MatchScore matchScore){
        boolean checkFirstPlayerAdditionalGameWin = matchScore.getFirstPlayerGames().equals(TennisScore.SIX.toString())
                && matchScore.getSecondPlayerGames().equals(TennisScore.FIVE.toString());

        boolean checkSecondPlayerAdditionalGameWin = matchScore.getFirstPlayerGames().equals(TennisScore.FIVE.toString())
                && matchScore.getSecondPlayerGames().equals(TennisScore.SIX.toString());

        return checkFirstPlayerAdditionalGameWin || checkSecondPlayerAdditionalGameWin;
    }

    private void handleSixFiveGameScore(MatchScore matchScore, Player player){

        if(player == Player.FIRST){
            String currentSets = matchScore.getFirstPlayerSets();
            TennisScore sets = TennisScore.fromString(currentSets);
            TennisScore newSetScore = calculateNextSetsScore(sets);
            updatePlayerSets(matchScore, player, newSetScore);
        }else{
            String currentSets = matchScore.getSecondPlayerSets();
            TennisScore sets = TennisScore.fromString(currentSets);
            TennisScore newSetScore = calculateNextSetsScore(sets);
            updatePlayerSets(matchScore, player, newSetScore);
        }
    }




}
