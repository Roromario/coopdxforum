package com.coopdxforum.coopDXforum.repositories;

import com.coopdxforum.coopDXforum.models.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.time.LocalDateTime;
import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

    List<Invitation> findByUser_IdAndEvent_Game_IdIn(Integer userId, Collection<Integer> gameIds);

    List<Invitation> findByUser_IdAndEvent_Game_DateStartGameAfter(
            Integer userId, LocalDateTime dateStartGame);
}