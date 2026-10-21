package com.wedding.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wedding.guestbook.dto.InviteRequest;
import com.wedding.guestbook.model.EventInvitation;
import com.wedding.guestbook.service.InvitationService;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendInvite(@RequestBody InviteRequest request) {
        try {
            EventInvitation invitation = invitationService.inviteCoHost(
                    request.getEventId(), 
                    request.getInviterId(), 
                    request.getInviteeUsername()
            );
            return ResponseEntity.ok(invitation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{invitationId}/respond")
    public ResponseEntity<?> respondInvite(
            @PathVariable Long invitationId, 
            @RequestParam("status") String status) {
        try {
            EventInvitation updatedInvitation = invitationService.respondInvitation(invitationId, status);
            return ResponseEntity.ok(updatedInvitation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}