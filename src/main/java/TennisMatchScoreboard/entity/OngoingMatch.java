package TennisMatchScoreboard.entity;

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

    public OngoingMatch(UUID uuid, Player firstPlayer, Player secondPlayer) {
        this.uuid = uuid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.matchScore = new MatchScore();
    }

}
