package TennisMatchScoreboard.service;

import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.entity.Player;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class OngoingMatchService {
    private static final Map<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();
    private final static OngoingMatchService INSTANCE = new OngoingMatchService();

    private OngoingMatchService() {

    }

    public static OngoingMatchService getInstance() {
        return INSTANCE;
    }


    public UUID createNewMatch(Player firstPlayer, Player secondPlayer) {
        UUID uuid = UUID.randomUUID();
        OngoingMatch match = new OngoingMatch(uuid, firstPlayer, secondPlayer);
        ongoingMatches.put(uuid, match);
        return uuid;
    }

    public OngoingMatch getMatch(UUID uuid) {
        return ongoingMatches.get(uuid);
    }

    //TODO Метод с логикой завершения матча (удалить из коллекции и запихать в БД)






//    public void setFirstPlayerScore(){
//        firstPlayerSetScore = currentMatchScore.getFirstPlayerScore()[0];
//    }
//
//    public int getFirstPlayerSet(){
//        return firstPlayerSetScore;
//    }
//
//    public void updateFirstPlayerSetScore() {
//        this.firstPlayerSetScore += 15;
//    }
//
//
//    public void setSecondPlayerScore(){
//        secondPlayerSetScore = currentMatchScore.getSecondPlayerScore()[0];
//    }
//
//    public int getSecondPlayerSet(){
//        return secondPlayerSetScore;
//    }
//
//    public void updateSecondPlayerSetScore() {
//        this.secondPlayerSetScore += 15;
//    }
//
//
//
//
//
//
//    public int getFirstPlayerGame(){
//        return currentMatchScore.getFirstPlayerScore()[1];
//    }
//
//    public int getFirstPlayerPoint(){
//        return currentMatchScore.getFirstPlayerScore()[2];
//    }
//
//
//
//    public int getSecondPlayerGame(){
//        return currentMatchScore.getSecondPlayerScore()[1];
//    }
//
//    public int getSecondPlayerPoint(){
//        return currentMatchScore.getSecondPlayerScore()[2];
//    }





}
