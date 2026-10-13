package com.coopdxforum.coopDXforum.models.dto;

public class UserGameDTO {

    private GameDTO game;
    private boolean joined;

    public GameDTO getGame() { return game; }
    public void setGame(GameDTO game) { this.game = game; }

    public boolean isJoined() { return joined; }
    public void setJoined(boolean joined) { this.joined = joined; }
}
