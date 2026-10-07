package com.coopdxforum.coopDXforum.converters;

import com.coopdxforum.coopDXforum.models.dto.InvitationDTO;
import com.coopdxforum.coopDXforum.models.entities.Invitation;

public final class InvitationConverter {

    private InvitationConverter() {}

    public static InvitationDTO toDTO(Invitation invitation) {
        InvitationDTO dto = new InvitationDTO();
        dto.setId(invitation.getId());
        dto.setUserId(invitation.getUser() == null ? null : invitation.getUser().getId());
        dto.setEventId(invitation.getEvent() == null ? null : invitation.getEvent().getId());
        return dto;
    }
}
