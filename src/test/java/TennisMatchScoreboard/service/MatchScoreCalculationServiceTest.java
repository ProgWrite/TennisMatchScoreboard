package TennisMatchScoreboard.service;


import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.TennisScore;
import org.junit.jupiter.api.Assertions;
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
        matchScore.updateFirstPlayerPoints(TennisScore.ONE);
        matchScore.updateSecondPlayerGames(TennisScore.FIVE);

        calculationService.gameScoreCalculation(SECOND_PLAYER_ACTION);
        String sets = matchScore.getSecondPlayerSets();

        assertThat(sets).isEqualTo("1");
    }


    @Test
    public void gameShouldContinue(){
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

}
