package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.models.entities.Event;
import com.coopdxforum.coopDXforum.repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Event> getEventsByUser(@PathVariable Integer userId) {
        return eventRepository.findByGame_HostUser_Id(userId);
    }

    @GetMapping("/game-type/{type}")
    public List<Event> getEventsByGameType(@PathVariable String type) {
        return eventRepository.findByGame_Type(type);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setGame(eventDetails.getGame());
                    event.setDescription(eventDetails.getDescription());
                    event.setStatus(eventDetails.getStatus());
                    event.setCreatedAt(eventDetails.getCreatedAt());
                    return ResponseEntity.ok(eventRepository.save(event));
                })
                .orElse(ResponseEntity.notFound().build());
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