package TennisMatchScoreboard.entity;

import TennisMatchScoreboard.enums.GameState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OngoingMatch {

    private UUID uuid;
    private Player firstPlayer;
    private Player secondPlayer;
    private MatchScore matchScore;
    private GameState gameState = GameState.PLAYING;

    public OngoingMatch(UUID uuid, Player firstPlayer, Player secondPlayer) {
        this.uuid = uuid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.matchScore = new MatchScore();
        this.gameState = GameState.PLAYING;
    }

}
