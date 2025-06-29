package TennisMatchScoreboard.enums;

public enum TieBreak {
   // TODO игра может длиться очень долго как тогда быть? Вводить кучу енамов? Может они не нужны? Надо обдумать этот момент.
    //TODO возможно просто сделать немного другую логику счета.

    LOVE("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7");





    private final String score;

    TieBreak(String score) {
        this.score = score;
    }

    public static TieBreak fromString(String value) {
        for (TieBreak score : values()) {
            if (score.toString().equals(value)) {
                return score;
            }
        }
        throw new IllegalArgumentException("Unknown score value: " + value);
    }

    @Override
    public String toString() {
        return score;
    }

    public TieBreak nextPointsScore() {
        return switch (this) {
            case LOVE -> ONE;
            case ONE -> TWO;
            case TWO -> THREE;
            case THREE -> FOUR;
            case FOUR -> FIVE;
            case FIVE -> SIX;
            case SIX -> SEVEN;
            default -> throw new IllegalStateException("No next points score after " + this);
        };
    }

}







