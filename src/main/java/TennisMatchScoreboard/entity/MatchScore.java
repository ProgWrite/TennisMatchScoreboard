package TennisMatchScoreboard.entity;


import TennisMatchScoreboard.enums.TennisScore;
import lombok.*;

@Getter
@Setter
public class MatchScore {

    private String[] firstPlayerScore =
            {TennisScore.LOVE.toString(), TennisScore.LOVE.toString(), TennisScore.LOVE.toString()};

    private String[] secondPlayerScore =
            {TennisScore.LOVE.toString(), TennisScore.LOVE.toString(), TennisScore.LOVE.toString()};


    public String getFirstPlayerPoints() {
        return firstPlayerScore[2];
    }

    public void updateFirstPlayerPoints(TennisScore newScore) {
        firstPlayerScore[2] = newScore.toString();
    }

    public String getSecondPlayerPoints() {
        return secondPlayerScore[2];
    }

    public void updateSecondPlayerPoints(TennisScore newScore){
        secondPlayerScore[2] = newScore.toString();
    }

    public String getFirstPlayerGames(){
        return firstPlayerScore[1];
    }

    public void updateFirstPlayerGames(TennisScore newScore){
        firstPlayerScore[1] = newScore.toString();
    }

    public String getSecondPlayerGames(){
        return secondPlayerScore[1];
    }

    public void updateSecondPlayerGames(TennisScore newScore){
        secondPlayerScore[1] = newScore.toString();
    }

    public String getFirstPlayerSets(){
        return firstPlayerScore[0];
    }

    public void updateFirstPlayerSets(TennisScore newScore){
        firstPlayerScore[0] = newScore.toString();
    }

    public String getSecondPlayerSets(){
        return secondPlayerScore[0];
    }

    public void updateSecondPlayerSets(TennisScore newScore){
        secondPlayerScore[0] = newScore.toString();
    }


}
