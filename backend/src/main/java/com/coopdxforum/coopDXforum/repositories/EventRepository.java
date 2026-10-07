package com.coopdxforum.coopDXforum.repositories;

import com.coopdxforum.coopDXforum.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByGame_HostUser_Id(Integer userId);

    List<Event> findByGame_Type(String type);
}