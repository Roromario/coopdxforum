package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.converters.InvitationConverter;
import com.coopdxforum.coopDXforum.models.dto.InvitationDTO;
import com.coopdxforum.coopDXforum.models.entities.Event;
import com.coopdxforum.coopDXforum.models.entities.Invitation;
import com.coopdxforum.coopDXforum.models.entities.User;
import com.coopdxforum.coopDXforum.repositories.EventRepository;
import com.coopdxforum.coopDXforum.repositories.InvitationRepository;
import com.coopdxforum.coopDXforum.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public InvitationController(
            InvitationRepository invitationRepository,
            UserRepository userRepository,
            EventRepository eventRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<InvitationDTO> getAllInvitations() {
        return invitationRepository.findAll().stream()
                .map(InvitationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationDTO> getInvitationById(@PathVariable Integer id) {
        return invitationRepository.findById(id)
                .map(InvitationConverter::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public InvitationDTO createInvitation(@RequestBody InvitationDTO invitationDTO) {
        Invitation invitation = new Invitation();
        invitation.setUser(findUser(invitationDTO.getUserId()));
        invitation.setEvent(findEvent(invitationDTO.getEventId()));
        return InvitationConverter.toDTO(invitationRepository.save(invitation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvitationDTO> updateInvitation(
            @PathVariable Integer id, @RequestBody InvitationDTO invitationDetails) {
        return invitationRepository.findById(id)
                .map(invitation -> {
                    invitation.setUser(findUser(invitationDetails.getUserId()));
                    invitation.setEvent(findEvent(invitationDetails.getEventId()));
                    return ResponseEntity.ok(InvitationConverter.toDTO(invitationRepository.save(invitation)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private User findUser(Integer userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private Event findEvent(Integer eventId) {
        if (eventId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventId is required");
        }
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Integer id) {
        if (invitationRepository.existsById(id)) {
            invitationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}