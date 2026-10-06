package com.coopdxforum.coopDXforum.controllers;

import com.coopdxforum.coopDXforum.models.entities.Invitation;
import com.coopdxforum.coopDXforum.repositories.InvitationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationRepository invitationRepository;

    public InvitationController(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @GetMapping
    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invitation> getInvitationById(@PathVariable Long id) {
        return invitationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Invitation createInvitation(@RequestBody Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invitation> updateInvitation(@PathVariable Long id, @RequestBody Invitation invitationDetails) {
        return invitationRepository.findById(id)
                .map(invitation -> {
                    invitation.setUser(invitationDetails.getUser());
                    invitation.setEvent(invitationDetails.getEvent());
                    return ResponseEntity.ok(invitationRepository.save(invitation));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        if (invitationRepository.existsById(id)) {
            invitationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}