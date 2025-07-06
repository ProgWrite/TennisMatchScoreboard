package TennisMatchScoreboard.service;

import TennisMatchScoreboard.dao.MatchDao;
import TennisMatchScoreboard.dto.PaginationDto;
import TennisMatchScoreboard.entity.Match;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.enums.TennisScore;
import TennisMatchScoreboard.exceptions.MatchProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
    private OngoingMatch ongoingMatch;
    private final MatchDao matchDao = MatchDao.getInstance();


    public FinishedMatchesPersistenceService(OngoingMatch ongoingMatch) {
        this.ongoingMatch = ongoingMatch;
    }

    public FinishedMatchesPersistenceService(){

    }

    public void persistFinishedMatch(Match match) {
        UUID uuid = ongoingMatch.getUuid();
        ongoingMatchService.removeMatch(uuid);
        matchDao.update(match);
    }

    public Match saveFinishedMatch(OngoingMatch ongoingMatch) {
        try {
            return Match.builder()
                    .player1(ongoingMatch.getFirstPlayer())
                    .player2(ongoingMatch.getSecondPlayer())
                    .winner(determineWinner(ongoingMatch))
                    .build();
        }catch (Exception e){
            throw new MatchProcessingException("Failed to save finished match" + e.getMessage());
        }

    }

    public PaginationDto<Match> getPaginationPages(String player, int page, int size){
        List<Match> matches = (player != null && !player.isEmpty())
                ? findFinishedMatchesByPlayerName(player)
                : getFinishedMatches();

        int totalItems = matches.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        page = Math.max(1, Math.min(page, totalPages));

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, totalItems);

        return new PaginationDto<>(
                matches.subList(fromIndex, toIndex),
                page,
                totalPages,
                totalItems
        );
    }

    private List<Match> getFinishedMatches() {
        return matchDao.findAll();
    }

    private List<Match> findFinishedMatchesByPlayerName(String playerName) {
        List<Match> matches = matchDao.findAll();
        return findMatchesByPlayerName(matches, playerName);
    }


    private Player determineWinner(OngoingMatch match){
        if(match.getMatchScore().getFirstPlayerSets().equals(TennisScore.TWO.toString())){
            return match.getFirstPlayer();
        }else{
            return match.getSecondPlayer();
        }
    }

    private List<Match> findMatchesByPlayerName(List<Match> matches, String playerName) {
        List<Match> matchesWithPlayerName = new ArrayList<>();
        for (Match match : matches) {
            if (match.getPlayer1().getName().equals(playerName)
                    || match.getPlayer2().getName().equals(playerName)) {
                matchesWithPlayerName.add(match);
            }
        }
        return matchesWithPlayerName;
    }

}
