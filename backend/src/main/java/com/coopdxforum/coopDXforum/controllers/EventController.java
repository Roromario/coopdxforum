package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.converters.EventConverter;
import com.coopdxforum.coopDXforum.models.dto.EventDTO;
import com.coopdxforum.coopDXforum.models.entities.Event;
import com.coopdxforum.coopDXforum.models.entities.Game;
import com.coopdxforum.coopDXforum.repositories.EventRepository;
import com.coopdxforum.coopDXforum.repositories.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;
    private final GameRepository gameRepository;

    public EventController(EventRepository eventRepository, GameRepository gameRepository) {
        this.eventRepository = eventRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<EventDTO> getEventsByUser(@PathVariable Integer userId) {
        return eventRepository.findByGame_HostUser_Id(userId).stream()
                .map(EventConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/game-type/{type}")
    public List<EventDTO> getEventsByGameType(@PathVariable String type) {
        return eventRepository.findByGame_Type(type).stream()
                .map(EventConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(EventConverter::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        event.setGame(findGame(eventDTO.getGameId()));
        event.setDescription(eventDTO.getDescription());
        event.setStatus(eventDTO.getStatus());
        event.setCreatedAt(eventDTO.getCreatedAt());
        return EventConverter.toDTO(eventRepository.save(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDetails) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setGame(findGame(eventDetails.getGameId()));
                    event.setDescription(eventDetails.getDescription());
                    event.setStatus(eventDetails.getStatus());
                    event.setCreatedAt(eventDetails.getCreatedAt());
                    return ResponseEntity.ok(EventConverter.toDTO(eventRepository.save(event)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private Game findGame(Integer gameId) {
        if (gameId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "gameId is required");
        }
        return gameRepository.findById(gameId.longValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}