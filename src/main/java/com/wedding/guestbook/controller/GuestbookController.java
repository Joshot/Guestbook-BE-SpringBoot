package com.wedding.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wedding.guestbook.dto.GuestbookRequest;
import com.wedding.guestbook.model.Guestbook;
import com.wedding.guestbook.service.GuestbookService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/guestbook")
public class GuestbookController {

    @Autowired
    private GuestbookService guestbookService;

    @PostMapping("/{eventCode}/upload")
    public ResponseEntity<?> uploadPhoto(
            @PathVariable String eventCode,
            @RequestBody GuestbookRequest request,
            HttpServletRequest httpRequest) {
        
        try {
            String guestIp = httpRequest.getRemoteAddr();
            Guestbook savedData = guestbookService.uploadGuestPhoto(
                    eventCode, 
                    request.getGuestName(), 
                    guestIp, 
                    request.getImageUrl(), 
                    request.getWishMessage()
            );
            return ResponseEntity.ok(savedData);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/gallery/{eventId}")
    public ResponseEntity<?> getEventGallery(@PathVariable Long eventId) {
        try {
            return ResponseEntity.ok(guestbookService.getGalleryByEvent(eventId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/photo/{photoId}")
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId) {
        try {
            guestbookService.deletePhoto(photoId);
            return ResponseEntity.ok("Foto berhasil dihapus dari galeri.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/export/{eventId}")
    public org.springframework.http.ResponseEntity<byte[]> exportZip(@PathVariable Long eventId) {
        try {
            byte[] zipBytes = guestbookService.exportPhotosToZip(eventId);
            
            return org.springframework.http.ResponseEntity.ok()
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"guestbook_photos.zip\"")
                    .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipBytes);
        } catch (RuntimeException e) {
            return org.springframework.http.ResponseEntity.badRequest().body(null);
        }
    }
}