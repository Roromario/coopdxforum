package com.coopdxforum.coopDXforum.converters;

import com.coopdxforum.coopDXforum.models.dto.EventDTO;
import com.coopdxforum.coopDXforum.models.entities.Event;

public final class EventConverter {

    private EventConverter() {}

    public static EventDTO toDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setGameId(event.getGame() == null ? null : event.getGame().getId());
        dto.setDescription(event.getDescription());
        dto.setStatus(event.getStatus());
        dto.setCreatedAt(event.getCreatedAt());
        return dto;
    }
}
