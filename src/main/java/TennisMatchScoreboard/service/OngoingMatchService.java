package TennisMatchScoreboard.service;

import TennisMatchScoreboard.dto.PlayerDto;
import TennisMatchScoreboard.entity.MatchScore;
import TennisMatchScoreboard.entity.MatchScoreModel;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OngoingMatchService {
    private final static OngoingMatchService INSTANCE = new OngoingMatchService();
    private static final int INITIAL_POINTS = 0;
    private final UUID matchId = UUID.randomUUID();
    private MatchScore currentMatchScore;



    private OngoingMatchService() {

    }

    public static OngoingMatchService getInstance() {
        return INSTANCE;
    }

    // TODO возможно тут понадобится ID!
    public void createOngoingMatch(PlayerDto firstPlayer, PlayerDto secondPlayer) {
        MatchScoreModel matchScoreModel = new MatchScoreModel(firstPlayer, secondPlayer);
        int[] firstPlayerScore = {INITIAL_POINTS, INITIAL_POINTS, INITIAL_POINTS};
        int[] secondPlayerScore = {INITIAL_POINTS, INITIAL_POINTS, INITIAL_POINTS};
        currentMatchScore = new MatchScore(firstPlayerScore, secondPlayerScore);
        matchScoreModel.addMatch(matchId, currentMatchScore);
    }


    public int getFirstPlayerSet(){
        return currentMatchScore.getFirstPlayerScore()[0];
    }

    public int getFirstPlayerGame(){
        return currentMatchScore.getFirstPlayerScore()[1];
    }

    public int getFirstPlayerPoint(){
        return currentMatchScore.getFirstPlayerScore()[2];
    }

    public int getSecondPlayerSet(){
        return currentMatchScore.getSecondPlayerScore()[0];
    }

    public int getSecondPlayerGame(){
        return currentMatchScore.getSecondPlayerScore()[1];
    }

    public int getSecondPlayerPoint(){
        return currentMatchScore.getSecondPlayerScore()[2];
    }




}
