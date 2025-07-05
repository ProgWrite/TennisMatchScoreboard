package TennisMatchScoreboard.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (cascade = {CascadeType.MERGE})
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private Player player1;

    @ManyToOne (cascade = {CascadeType.MERGE})
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Player winner;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return id == match.id && player1 == match.player1 && player2 == match.player2 && winner == match.winner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player1, player2, winner);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", winner=" + winner +
                '}';
    }
}
