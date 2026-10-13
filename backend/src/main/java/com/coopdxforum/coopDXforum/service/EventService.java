package com.coopdxforum.coopDXforum.service;

import com.coopdxforum.coopDXforum.converters.EventConverter;
import com.coopdxforum.coopDXforum.models.dto.EventDTO;
import com.coopdxforum.coopDXforum.models.entities.Event;
import com.coopdxforum.coopDXforum.models.entities.Game;
import com.coopdxforum.coopDXforum.repositories.EventRepository;
import com.coopdxforum.coopDXforum.repositories.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final GameRepository gameRepository;

    public EventService(EventRepository eventRepository, GameRepository gameRepository) {
        this.eventRepository = eventRepository;
        this.gameRepository = gameRepository;
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> getEventsByUser(Integer userId) {
        return eventRepository.findByGame_HostUser_Id(userId).stream()
                .map(EventConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> getEventsByGameType(String type) {
        return eventRepository.findByGame_Type(type).stream()
                .map(EventConverter::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(Integer id) {
        return eventRepository.findById(id)
                .map(EventConverter::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = new Event();
        applyEventDetails(event, eventDTO);
        return EventConverter.toDTO(eventRepository.save(event));
    }

    @Transactional
    public EventDTO updateEvent(Integer id, EventDTO eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        applyEventDetails(event, eventDetails);
        return EventConverter.toDTO(eventRepository.save(event));
    }

    @Transactional
    public void deleteEvent(Integer id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        eventRepository.deleteById(id);
    }

    private void applyEventDetails(Event event, EventDTO eventDetails) {
        event.setGame(findGame(eventDetails.getGameId()));
        event.setDescription(eventDetails.getDescription());
        event.setStatus(eventDetails.getStatus());
        event.setCreatedAt(eventDetails.getCreatedAt());
    }

    private Game findGame(Integer gameId) {
        if (gameId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "gameId is required");
        }
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }
}
