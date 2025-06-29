package TennisMatchScoreboard.enums;


//TODO возможно разделю на 3 енама (подумаю) + TieBreak
public enum TennisScore {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE("AD"),

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6");


    private final String score;

    TennisScore(String score) {
        this.score = score;
    }

    public static TennisScore fromString(String value) {
        for (TennisScore score : values()) {
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


    public TennisScore nextPointsScore() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            default -> throw new IllegalStateException("No next points score after " + this);
        };
    }

    public TennisScore nextGamesScore() {
        return switch (this) {
            case LOVE -> ONE;
            case ONE -> TWO;
            case TWO -> THREE;
            case THREE -> FOUR;
            case FOUR -> FIVE;
            case FIVE -> SIX;
            default -> throw new IllegalStateException("No next games score after " + this);
        };
    }

    public TennisScore nextSetsScore() {
        return switch (this) {
            case LOVE -> ONE;
            case ONE -> TWO;
            default -> throw new IllegalStateException("No next sets score after " + this);
        };
    }

}
