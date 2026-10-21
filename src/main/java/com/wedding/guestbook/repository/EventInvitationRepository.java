package com.wedding.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wedding.guestbook.model.EventInvitation;
import java.util.List;

@Repository
public interface EventInvitationRepository extends JpaRepository<EventInvitation, Long> {
    List<EventInvitation> findByInviteeIdAndStatus(Long inviteeId, String status);
}