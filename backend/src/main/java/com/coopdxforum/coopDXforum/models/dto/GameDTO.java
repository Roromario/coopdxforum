package com.coopdxforum.coopDXforum.models.dto;

import java.time.LocalDateTime;

public class GameDTO {

    private Integer id;
    private Integer hostUserId;
    private String type;
    private String code;
    private LocalDateTime dateStartGame;
    private LocalDateTime dateEndGame;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getHostUserId() { return hostUserId; }
    public void setHostUserId(Integer hostUserId) { this.hostUserId = hostUserId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public LocalDateTime getDateStartGame() { return dateStartGame; }
    public void setDateStartGame(LocalDateTime dateStartGame) { this.dateStartGame = dateStartGame; }

    public LocalDateTime getDateEndGame() { return dateEndGame; }
    public void setDateEndGame(LocalDateTime dateEndGame) { this.dateEndGame = dateEndGame; }
}
