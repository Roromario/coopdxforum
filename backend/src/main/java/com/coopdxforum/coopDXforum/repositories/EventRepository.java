package com.coopdxforum.coopDXforum.repositories;

import com.coopdxforum.coopDXforum.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}