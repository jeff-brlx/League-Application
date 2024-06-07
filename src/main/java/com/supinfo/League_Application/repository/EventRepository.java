package com.supinfo.League_Application.repository;



import com.supinfo.League_Application.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}