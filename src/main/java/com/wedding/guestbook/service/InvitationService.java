package com.wedding.guestbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wedding.guestbook.model.Event;
import com.wedding.guestbook.model.EventInvitation;
import com.wedding.guestbook.model.User;
import com.wedding.guestbook.repository.EventInvitationRepository;
import com.wedding.guestbook.repository.EventRepository;
import com.wedding.guestbook.repository.UserRepository;

@Service
public class InvitationService {

    @Autowired
    private EventInvitationRepository invitationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Host Mengirim Undangan
    public EventInvitation inviteCoHost(Long eventId, Long inviterId, String inviteeUsername) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Acara tidak ditemukan!"));
        
        User inviter = userRepository.findById(inviterId)
                .orElseThrow(() -> new RuntimeException("Host tidak ditemukan!"));
                
        User invitee = userRepository.findByUsername(inviteeUsername)
                .orElseThrow(() -> new RuntimeException("Username teman tidak ditemukan!"));

        if (inviter.getId().equals(invitee.getId())) {
            throw new RuntimeException("Tidak bisa mengundang diri sendiri!");
        }

        EventInvitation invitation = new EventInvitation();
        invitation.setEvent(event);
        invitation.setInviter(inviter);
        invitation.setInvitee(invitee);
        invitation.setStatus("PENDING"); 

        return invitationRepository.save(invitation);
    }

    public EventInvitation respondInvitation(Long invitationId, String statusRespon) {
        EventInvitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Undangan tidak ditemukan!"));
        
        invitation.setStatus(statusRespon.toUpperCase());
        return invitationRepository.save(invitation);
    }
}