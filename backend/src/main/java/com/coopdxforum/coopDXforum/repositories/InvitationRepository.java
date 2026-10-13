package com.coopdxforum.coopDXforum.repositories;

import com.coopdxforum.coopDXforum.models.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

    List<Invitation> findByUser_IdAndEvent_Game_IdIn(Integer userId, Collection<Integer> gameIds);

    List<Invitation> findByUser_IdAndEvent_Game_DateStartGameAfter(
            Integer userId, LocalDateTime dateStartGame);

    @Query("""
            SELECT invitation
            FROM Invitation invitation
            JOIN FETCH invitation.event event
            JOIN FETCH event.game game
            WHERE invitation.id = :id
            """)
    Optional<Invitation> findByIdWithEventAndGame(@Param("id") Integer id);
}