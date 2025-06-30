package TennisMatchScoreboard.service.matchScoreCalcultaion;

import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.enums.GameState;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.enums.TieBreak;

public class TieBreakInitializer {
    private final OngoingMatch ongoingMatch;

    public TieBreakInitializer(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public void startTieBreakRules(MatchScore matchScore) {
        matchScore.updateFirstPlayerGames(TennisScore.SIX);
        matchScore.updateSecondPlayerGames(TennisScore.SIX);
        matchScore.updateFirstPlayerPoints(TieBreak.LOVE);
        matchScore.updateSecondPlayerPoints(TieBreak.LOVE);
        ongoingMatch.setGameState(GameState.TIE_BREAK);
    }

}
