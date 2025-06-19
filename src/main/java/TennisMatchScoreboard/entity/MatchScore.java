package TennisMatchScoreboard.entity;


import lombok.*;

@Getter
@Setter
public class MatchScore {
    private final static int INITIAL_SETS = 0;
    private final static int INITIAL_GAMES = 0;
    private final static int INITIAL_POINTS = 0;

    private int[] firstPlayerScore = {INITIAL_SETS, INITIAL_GAMES, INITIAL_POINTS};
    private int[] secondPlayerScore = {INITIAL_SETS, INITIAL_GAMES, INITIAL_POINTS};

    public int getFirstPlayerPoints() {
        return firstPlayerScore[2];
    }

    public void updateFirstPlayerPoints(int points){
        firstPlayerScore[2] += points;
    }

    public int getSecondPlayerPoints() {
        return secondPlayerScore[2];
    }

    public void updateSecondPlayerPoints(int points){
        secondPlayerScore[2] += points;
    }

    public int getFirstPlayerGames(){
        return firstPlayerScore[1];
    }

    public void updateFirstPlayerGames(int points){
        firstPlayerScore[1] += points;
    }

    public int getSecondPlayerGames(){
        return secondPlayerScore[1];
    }

    public void updateSecondPlayerGames(int points){
        secondPlayerScore[1] += points;
    }

    public int getFirstPlayerSets(){
        return firstPlayerScore[0];
    }

    public void updateFirstPlayerSets(int points){
        firstPlayerScore[0] += points;
    }

    public int getSecondPlayerSets(){
        return secondPlayerScore[0];
    }

    public void updateSecondPlayerSets(int points){
        secondPlayerScore[0] += points;
    }


}
