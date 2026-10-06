package com.coopdxforum.coopDXforum.repositories;

import com.coopdxforum.coopDXforum.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}