package com.coopdxforum.coopDXforum.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"Games\"")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_host", nullable = false)
    private User hostUser;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "date_start_game", nullable = false)
    private LocalDateTime dateStartGame;

    @Column(name = "date_end_game")
    private LocalDateTime dateEndGame;

    public Game() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getHostUser() { return hostUser; }
    public void setHostUser(User hostUser) { this.hostUser = hostUser; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public LocalDateTime getDateStartGame() { return dateStartGame; }
    public void setDateStartGame(LocalDateTime dateStartGame) { this.dateStartGame = dateStartGame; }

    public LocalDateTime getDateEndGame() { return dateEndGame; }
    public void setDateEndGame(LocalDateTime dateEndGame) { this.dateEndGame = dateEndGame; }
}