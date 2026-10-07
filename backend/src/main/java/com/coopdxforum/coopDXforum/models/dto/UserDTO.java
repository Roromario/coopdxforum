package com.coopdxforum.coopDXforum.models.dto;

import java.time.LocalDateTime;

public class UserDTO {

    private Integer id;
    private String nickname;
    private String globalName;
    private String pp;
    private LocalDateTime lastConnection;
    private String mailAddress;

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
