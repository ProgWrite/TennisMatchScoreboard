package TennisMatchScoreboard.service;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;

public class MatchScoreCalculationService {
    private final OngoingMatch ongoingMatch;
    private final int ZERO = 0;
    private final int FIVE = 5;
    private final int TEN = 10;
    private final int FIFTEEN = 15;
    private final int THIRTY = 30;
    private final int FORTY = 40;

    private final int NEGATIVE_FIVE = -FIVE;
    private final int NEGATIVE_FORTY = -FORTY;

    private final int ONE = 1;

    // для AD логика


    public MatchScoreCalculationService(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    //TODO метод однозначно буду дробить
    public void updateFirstPlayerScore() {
        MatchScore matchScore = ongoingMatch.getMatchScore();
        int currentPoints = matchScore.getFirstPlayerPoints();
        int currentGames = matchScore.getFirstPlayerGames();
        int currentSets = matchScore.getFirstPlayerSets();

        if(currentPoints >= FORTY) {
            if(currentGames == FIVE) {
                matchScore.updateFirstPlayerSets(ONE);
                matchScore.updateFirstPlayerGames(NEGATIVE_FIVE);
                matchScore.updateFirstPlayerPoints(NEGATIVE_FORTY);
            }else {
                matchScore.updateFirstPlayerPoints(NEGATIVE_FORTY);
                matchScore.updateFirstPlayerGames(ONE);
            }
        }

        if (currentPoints == ZERO  || currentPoints == FIFTEEN) {
            matchScore.updateFirstPlayerPoints(FIFTEEN);
        }

        if (currentPoints == THIRTY) {
            matchScore.updateFirstPlayerPoints(TEN);
        }

    }


    public void updateSecondPlayerScore() {
        MatchScore matchScore = ongoingMatch.getMatchScore();
        int currentPoints = matchScore.getSecondPlayerPoints();
        int currentGames = matchScore.getSecondPlayerGames();
        int currentSets = matchScore.getSecondPlayerSets();

        if(currentPoints >= FORTY) {
            if(currentGames == FIVE) {
                matchScore.updateSecondPlayerSets(ONE);
                matchScore.updateSecondPlayerGames(NEGATIVE_FIVE);
                matchScore.updateSecondPlayerPoints(NEGATIVE_FORTY);
            }else {
                matchScore.updateSecondPlayerPoints(NEGATIVE_FORTY);
                matchScore.updateSecondPlayerGames(ONE);
            }
        }

        if (currentPoints == ZERO  || currentPoints == FIFTEEN) {
            matchScore.updateSecondPlayerPoints(FIFTEEN);
        }

        if (currentPoints == THIRTY) {
            matchScore.updateSecondPlayerPoints(TEN);
        }

    }

}
