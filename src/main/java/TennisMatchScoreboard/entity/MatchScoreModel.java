package TennisMatchScoreboard.entity;

import TennisMatchScoreboard.dto.PlayerDto;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//todo ОЧЕНЬ сомневаюсь в этом месте
public class MatchScoreModel {
    private final PlayerDto firstPlayer;
    private final PlayerDto secondPlayer;

    public MatchScoreModel(PlayerDto firstPlayer, PlayerDto secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    private final Map<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    public void addMatch(UUID matchId, MatchScore matchScore) {
        ongoingMatches.put(matchId, matchScore);
    }


    // TODO потом сделай свое исключение
    public MatchScore getMatchScore(UUID matchId) {
        MatchScore matchScore = ongoingMatches.get(matchId);
        if (matchScore == null) {
            throw new IllegalArgumentException("Match not found");
        }
        return matchScore;
    }

    public void updateMatchScore(UUID matchId) {
        int[] player1Score = new int[0];
        player1Score[0] = 1;
        int[] player2Score = new int[0];
        player2Score[0] = 1;

        ongoingMatches.put(matchId, new MatchScore(player1Score, player2Score));
    }

}
