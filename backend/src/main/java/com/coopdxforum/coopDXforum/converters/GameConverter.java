package com.coopdxforum.coopDXforum.converters;

import com.coopdxforum.coopDXforum.models.dto.GameDTO;
import com.coopdxforum.coopDXforum.models.entities.Game;

public final class GameConverter {

    private GameConverter() {}

    public static GameDTO toDTO(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setHostUserId(game.getHostUser() == null ? null : game.getHostUser().getId());
        dto.setType(game.getType());
        dto.setCode(game.getCode());
        dto.setDateStartGame(game.getDateStartGame());
        dto.setDateEndGame(game.getDateEndGame());
        return dto;
    }
}
