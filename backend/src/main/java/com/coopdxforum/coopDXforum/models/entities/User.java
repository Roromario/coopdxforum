package com.coopdxforum.coopDXforum.models.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"Users\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "global_name")
    private String globalName;

    @Column(name = "pp")
    private String pp;

    @Column(name = "last_connection")
    private LocalDateTime lastConnection;

    @Column(name = "mail_address", nullable = false, unique = true)
    private String mailAddress;

    public User() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getGlobalName() { return globalName; }
    public void setGlobalName(String globalName) { this.globalName = globalName; }

    public String getPp() { return pp; }
    public void setPp(String pp) { this.pp = pp; }

    public LocalDateTime getLastConnection() { return lastConnection; }
    public void setLastConnection(LocalDateTime lastConnection) { this.lastConnection = lastConnection; }

    public String getMailAddress() { return mailAddress; }
    public void setMailAddress(String mailAddress) { this.mailAddress = mailAddress; }
}