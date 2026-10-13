package com.coopdxforum.coopDXforum.repositories;

import com.coopdxforum.coopDXforum.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("""
            SELECT game
            FROM Game game
            WHERE game.hostUser.id = :userId
               OR EXISTS (
                    SELECT invitation.id
                    FROM Invitation invitation
                    WHERE invitation.user.id = :userId
                      AND invitation.event.game = game
               )
            """)
    List<Game> findAllGamesByUserId(@Param("userId") Integer userId);

    @Query("""
            SELECT DISTINCT game
            FROM Game game
            JOIN Event event ON event.game = game
            JOIN Invitation invitation ON invitation.event = event
            WHERE invitation.user.id = :userId
              AND game.hostUser.id <> :userId
              AND (game.dateEndGame IS NULL OR game.dateEndGame >= :now)
            """)
    List<Game> findParticipatingGamesByUserId(
            @Param("userId") Integer userId, @Param("now") LocalDateTime now);

    @Query("""
            SELECT DISTINCT game
            FROM Game game
            JOIN Event event ON event.game = game
            JOIN Invitation invitation ON invitation.event = event
            WHERE invitation.user.id = :userId
              AND game.hostUser.id <> :userId
              AND game.dateEndGame < :now
            """)
    List<Game> findPreviouslyParticipatedGamesByUserId(
            @Param("userId") Integer userId, @Param("now") LocalDateTime now);
}