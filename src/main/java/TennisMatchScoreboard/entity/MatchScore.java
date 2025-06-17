package TennisMatchScoreboard.entity;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchScore {

    private int[] firstPlayerScore;
    private int[] secondPlayerScore;


}
