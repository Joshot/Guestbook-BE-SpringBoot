package com.wedding.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wedding.guestbook.dto.EventRequest;
import com.wedding.guestbook.model.Event;
import com.wedding.guestbook.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest request) {
        try {
            Event createdEvent = eventService.createEvent(request.getOwnerId(), request.getEventName());
            return ResponseEntity.ok(createdEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}