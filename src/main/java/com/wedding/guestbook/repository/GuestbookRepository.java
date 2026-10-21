package com.wedding.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wedding.guestbook.model.Guestbook;
import java.util.List;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    long countByEventIdAndGuestIp(Long eventId, String guestIp);
    
    List<Guestbook> findByEventIdOrderByCreatedAtDesc(Long eventId);
}