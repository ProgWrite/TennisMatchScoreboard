package TennisMatchScoreboard.mapper;

import TennisMatchScoreboard.dto.PlayerDto;
import TennisMatchScoreboard.entity.Player;

public class PlayerMapper implements Mapper<PlayerDto, Player> {

    private final static PlayerMapper INSTANCE = new PlayerMapper();

    public static PlayerMapper getInstance() {
        return INSTANCE;
    }

    PlayerMapper() {

    }

    @Override
    public Player mapToEntity(PlayerDto playerDto) {
        return Player.builder()
                .name(playerDto.name())
                .build();
    }

    @Override
    public PlayerDto mapToDto(Player player) {
        return PlayerDto.builder()
                .name(player.getName())
                .build();
    }


}
