package com.coopdxforum.coopDXforum.converters;

import com.coopdxforum.coopDXforum.models.dto.UserDTO;
import com.coopdxforum.coopDXforum.models.entities.User;

public final class UserConverter {

    private UserConverter() {}

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNickname(user.getNickname());
        dto.setGlobalName(user.getGlobalName());
        dto.setPp(user.getPp());
        dto.setLastConnection(user.getLastConnection());
        dto.setMailAddress(user.getMailAddress());
        return dto;
    }
}
