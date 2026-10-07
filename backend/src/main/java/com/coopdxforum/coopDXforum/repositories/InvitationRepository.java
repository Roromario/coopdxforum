package com.coopdxforum.coopDXforum.repositories;

import com.coopdxforum.coopDXforum.models.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
}