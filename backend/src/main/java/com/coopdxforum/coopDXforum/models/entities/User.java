package com.coopdxforum.coopDXforum.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateurs") // ou "users" selon le nom exact dans ton schema.sql
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String nickname;
    private String avatar;

    @Column(name = "last_connection")
    private LocalDateTime lastConnection;

    @Column(name = "address_mail", nullable = false)
    private String addressMail;

    // Constructeurs
    public User() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public LocalDateTime getLastConnection() { return lastConnection; }
    public void setLastConnection(LocalDateTime lastConnection) { this.lastConnection = lastConnection; }

    public String getAddressMail() { return addressMail; }
    public void setAddressMail(String addressMail) { this.addressMail = addressMail; }
}