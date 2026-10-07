package com.coopdxforum.coopDXforum.models.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private Integer id;
    private Integer gameId;
    private String description;
    private Short status;
    private LocalDateTime createdAt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getGameId() { return gameId; }
    public void setGameId(Integer gameId) { this.gameId = gameId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
