package com.coopdxforum.coopDXforum.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"Events\"")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private Short status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Event() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}