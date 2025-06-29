package TennisMatchScoreboard.service;


import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.GameState;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.enums.TieBreak;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

//TODO если тесты разрастутся то можно подумать про вложенные классы
public class MatchScoreCalculationServiceTest {
    private MatchScore matchScore;
    private OngoingMatch ongoingMatch;
    private MatchScoreCalculationService calculationService;
    private static final String FIRST_PLAYER_ACTION = "player1";
    private static final String SECOND_PLAYER_ACTION = "player2";

    // TODO отдельно все работает хорошо, но вместе не очень. После рефакторинга надо разобраться с этим


    @BeforeEach
    void setUp() {
        matchScore = new MatchScore();
        ongoingMatch = new OngoingMatch();
        ongoingMatch.setMatchScore(matchScore);
        calculationService = new MatchScoreCalculationService(ongoingMatch);
    }

    @Test
    public void firstPlayerShouldWinGamePoint(){
        matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
        matchScore.updateSecondPlayerPoints(TennisScore.LOVE);
        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);
        String games = matchScore.getFirstPlayerGames();

        assertThat(games).isEqualTo("1");
    }


    @Test
    public void secondPlayerShouldWinSetPoint(){
        matchScore.updateFirstPlayerPoints(TennisScore.FIFTEEN);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
        matchScore.updateFirstPlayerGames(TennisScore.ONE);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);

        calculationService.gameScoreCalculation(SECOND_PLAYER_ACTION);
        String sets = matchScore.getSecondPlayerSets();

        assertThat(sets).isEqualTo("1");
    }


    @Test
    public void shouldStartAdditionalGame(){
        matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
        matchScore.updateSecondPlayerPoints(TennisScore.THIRTY);
        matchScore.updateFirstPlayerGames(TennisScore.FIVE);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);

        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);
        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String sets = matchScore.getFirstPlayerSets();


        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("6"),
                ()-> assertThat(secondPlayerGames).isEqualTo("5"),
                ()->  assertThat(sets).isEqualTo("0")
        );
    }

    @Test
    public void shouldStartAdditionalGame2(){
        matchScore.updateFirstPlayerPoints(TennisScore.THIRTY);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
        matchScore.updateFirstPlayerGames(TennisScore.FIVE);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);

        calculationService.gameScoreCalculation(SECOND_PLAYER_ACTION);
        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String sets = matchScore.getFirstPlayerSets();


        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("5"),
                ()-> assertThat(secondPlayerGames).isEqualTo("6"),
                ()->  assertThat(sets).isEqualTo("0")
        );
    }



    @Test
    public void shouldStartAdvantageScenario() {
        matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
        String games = matchScore.getFirstPlayerGames();
        ongoingMatch.setMatchScore(matchScore);
        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);

        assertAll(
                ()-> assertThat(TennisScore.ADVANTAGE.toString()).isEqualTo(matchScore.getFirstPlayerPoints()),
                ()-> assertThat(TennisScore.FORTY.toString()).isEqualTo(matchScore.getSecondPlayerPoints()),
                ()-> assertThat(games).isEqualTo("0")
        );

    }

    @Test
    public void firstPlayerShouldWinGameAfterAdvantageScenario() {
        matchScore.updateFirstPlayerPoints(TennisScore.ADVANTAGE);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
        ongoingMatch.setMatchScore(matchScore);
        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);
        String games = matchScore.getFirstPlayerGames();

        assertAll(
                ()->   assertThat(games).isEqualTo("1"),
                ()-> assertThat(TennisScore.LOVE.toString()).isEqualTo(matchScore.getFirstPlayerPoints()),
                ()-> assertThat(TennisScore.LOVE.toString()).isEqualTo(matchScore.getSecondPlayerPoints())
        );
    }


    @Test
    public void secondPlayerScoreShouldChangeToForty() {
        matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
        matchScore.updateSecondPlayerPoints(TennisScore.ADVANTAGE);
        ongoingMatch.setMatchScore(matchScore);
        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);
        assertThat(TennisScore.FORTY.toString()).isEqualTo(matchScore.getSecondPlayerPoints());
    }

    @Test
    public void testSixFiveGameScoreIfFirstPlayerWin(){
        matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
        matchScore.updateSecondPlayerPoints(TennisScore.THIRTY);
        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);

        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String sets = matchScore.getFirstPlayerSets();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("0"),
                ()-> assertThat(secondPlayerGames).isEqualTo("0"),
                ()->  assertThat(sets).isEqualTo("1")
        );

    }

    @Test
    public void testSixFiveGameScoreIfSecondPlayerWin(){
        matchScore.updateFirstPlayerPoints(TennisScore.THIRTY);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
        matchScore.updateFirstPlayerGames(TennisScore.FIVE);
        matchScore.updateSecondPlayerGames(TennisScore.SIX);

        calculationService.gameScoreCalculation(SECOND_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String sets = matchScore.getSecondPlayerSets();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("0"),
                ()-> assertThat(secondPlayerGames).isEqualTo("0"),
                ()->  assertThat(sets).isEqualTo("1")
        );

    }


    @Test
    public void testFiveFiveGameScoreAndAdvantageScenario(){
        matchScore.updateFirstPlayerPoints(TennisScore.ADVANTAGE);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
        matchScore.updateFirstPlayerGames(TennisScore.FIVE);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);

        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("6"),
                ()-> assertThat(secondPlayerGames).isEqualTo("5")
        );

    }

    @Test
    public void testSixFiveGameScoreAndAdvantageScenario(){
        matchScore.updateFirstPlayerPoints(TennisScore.ADVANTAGE);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);
        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);

        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String firstPlayerSets = matchScore.getFirstPlayerSets();

        assertAll(
                ()-> assertThat(firstPlayerSets).isEqualTo("1"),
                ()-> assertThat(firstPlayerGames).isEqualTo("0"),
                ()-> assertThat(secondPlayerGames).isEqualTo("0")
        );

    }


    @Test
    public void shouldStartTieBreak(){

        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);
        matchScore.updateFirstPlayerPoints(TennisScore.LOVE);
        matchScore.updateSecondPlayerPoints(TennisScore.FORTY);

        calculationService.gameScoreCalculation(SECOND_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String firstPlayerPoints = matchScore.getFirstPlayerPoints();
        String secondPlayerPoints = matchScore.getSecondPlayerPoints();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("6"),
                ()-> assertThat(secondPlayerGames).isEqualTo("6"),
                ()-> assertThat(firstPlayerPoints).isEqualTo("0"),
                ()-> assertThat(secondPlayerPoints).isEqualTo("0")
        );

    }

    @Test
    public void shouldStartTieBreakAfterAdvantageScenario(){
        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);
        matchScore.updateFirstPlayerPoints(TennisScore.FORTY);
        matchScore.updateSecondPlayerPoints(TennisScore.ADVANTAGE);

        calculationService.gameScoreCalculation(SECOND_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String firstPlayerPoints = matchScore.getFirstPlayerPoints();
        String secondPlayerPoints = matchScore.getSecondPlayerPoints();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("6"),
                ()-> assertThat(secondPlayerGames).isEqualTo("6"),
                ()-> assertThat(firstPlayerPoints).isEqualTo("0"),
                ()-> assertThat(secondPlayerPoints).isEqualTo("0")
        );
    }

    @Test
    public void shouldPlayTieBreakRules(){
        ongoingMatch.setGameState(GameState.TIE_BREAK);
        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.SIX);
        matchScore.updateFirstPlayerPoints(TieBreak.LOVE);
        matchScore.updateSecondPlayerPoints(TieBreak.LOVE);

        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String firstPlayerPoints = matchScore.getFirstPlayerPoints();
        String secondPlayerPoints = matchScore.getSecondPlayerPoints();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("6"),
                ()-> assertThat(secondPlayerGames).isEqualTo("6"),
                ()-> assertThat(firstPlayerPoints).isEqualTo("1"),
                ()-> assertThat(secondPlayerPoints).isEqualTo("0")
        );
    }

    @Test
    public void shouldEndTieBreak(){
        ongoingMatch.setGameState(GameState.TIE_BREAK);
        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.SIX);
        matchScore.updateFirstPlayerPoints(TieBreak.SIX);
        matchScore.updateSecondPlayerPoints(TieBreak.TWO);

        calculationService.gameScoreCalculation(FIRST_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String firstPlayerSets = matchScore.getFirstPlayerSets();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("0"),
                ()-> assertThat(secondPlayerGames).isEqualTo("0"),
                ()-> assertThat(firstPlayerSets).isEqualTo("1")
        );
    }

    @Test
    public void shouldContinueTieBreak(){
        ongoingMatch.setGameState(GameState.TIE_BREAK);
        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.SIX);
        matchScore.updateFirstPlayerPoints(TieBreak.SIX);
        matchScore.updateSecondPlayerPoints(TieBreak.FIVE);

        calculationService.gameScoreCalculation(SECOND_PLAYER_ACTION);

        String firstPlayerGames = matchScore.getFirstPlayerGames();
        String secondPlayerGames = matchScore.getSecondPlayerGames();
        String firstPlayerPoints = matchScore.getFirstPlayerPoints();
        String secondPlayerPoints = matchScore.getSecondPlayerPoints();

        assertAll(
                ()-> assertThat(firstPlayerGames).isEqualTo("6"),
                ()-> assertThat(secondPlayerGames).isEqualTo("6"),
                ()-> assertThat(firstPlayerPoints).isEqualTo("6"),
                ()-> assertThat(secondPlayerPoints).isEqualTo("6")
        );
    }

}
