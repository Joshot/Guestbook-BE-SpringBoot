package com.wedding.guestbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wedding.guestbook.model.Event;
import com.wedding.guestbook.model.User;
import com.wedding.guestbook.repository.EventRepository;
import com.wedding.guestbook.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public Event createEvent(Long ownerId, String eventName) {
    	
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Host tidak ditemukan!"));

        List<Event> existingEvents = eventRepository.findByOwnerId(ownerId);
        for (Event event : existingEvents) {
            if ("ACTIVE".equals(event.getStatus())) {
                throw new RuntimeException("Gagal: Anda masih memiliki acara yang sedang aktif! Nonaktifkan dulu.");
            }
        }

        Event newEvent = new Event();
        newEvent.setOwner(owner);
        newEvent.setEventName(eventName);
        
        String uniqueCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        newEvent.setEventCode(uniqueCode);
        
        newEvent.setStatus("ACTIVE");

        return eventRepository.save(newEvent);
    }
}