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

    public void removeMatch(UUID uuid) {
        ongoingMatches.remove(uuid);
    }

}
