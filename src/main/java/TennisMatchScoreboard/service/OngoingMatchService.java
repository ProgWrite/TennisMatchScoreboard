package TennisMatchScoreboard.service;

import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.exceptions.NotFoundException;
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
        OngoingMatch match = ongoingMatches.get(uuid);
        if(match == null) {
            throw new NotFoundException("Match not found with UUID: " + uuid);
        }
        return match;
    }

    public void removeMatch(UUID uuid) {
        ongoingMatches.remove(uuid);
    }

}
