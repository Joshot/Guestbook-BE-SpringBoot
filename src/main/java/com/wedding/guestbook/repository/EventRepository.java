package com.wedding.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wedding.guestbook.model.Event;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByOwnerId(Long ownerId);
    
    Optional<Event> findByEventCode(String eventCode);
}