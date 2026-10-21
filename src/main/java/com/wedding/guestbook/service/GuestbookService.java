package com.wedding.guestbook.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wedding.guestbook.model.Event;
import com.wedding.guestbook.model.Guestbook;
import com.wedding.guestbook.repository.EventRepository;
import com.wedding.guestbook.repository.GuestbookRepository;

@Service
public class GuestbookService {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Autowired
    private EventRepository eventRepository;

    public Guestbook uploadGuestPhoto(String eventCode, String guestName, String guestIp, 
                                      String imageUrl, String wishMessage) {
        
        Event event = eventRepository.findByEventCode(eventCode)
                .orElseThrow(() -> new RuntimeException("Acara tidak ditemukan!"));

        long currentUploads = guestbookRepository.countByEventIdAndGuestIp(event.getId(), guestIp);
        if (currentUploads >= 10) {
            throw new RuntimeException("Batas unggah tercapai! Anda sudah mengirim 10 foto.");
        }

        Guestbook guestbook = new Guestbook();
        guestbook.setEvent(event);
        guestbook.setGuestName(guestName);
        guestbook.setGuestIp(guestIp);
        guestbook.setImageUrl(imageUrl);
        guestbook.setWishMessage(wishMessage);

        return guestbookRepository.save(guestbook);
    }

    public List<Guestbook> getGalleryByEvent(Long eventId) {
        return guestbookRepository.findByEventIdOrderByCreatedAtDesc(eventId);
    }

    public void deletePhoto(Long photoId) {
        if (!guestbookRepository.existsById(photoId)) {
            throw new RuntimeException("Foto tidak ditemukan!");
        }
        guestbookRepository.deleteById(photoId);
    }

    public byte[] exportPhotosToZip(Long eventId) {
        List<Guestbook> photos = guestbookRepository.findByEventIdOrderByCreatedAtDesc(eventId);
        if (photos.isEmpty()) {
            throw new RuntimeException("Tidak ada foto untuk di-export.");
        }

        try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
             java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(baos)) {

            for (Guestbook photo : photos) {
                try {
                    java.net.URL url = new java.net.URL(photo.getImageUrl());
                    try (java.io.InputStream is = url.openStream()) {
                        String fileName = photo.getGuestName().replaceAll("[^a-zA-Z0-9.-]", "_") + "_" + photo.getId() + ".jpg";
                        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry(fileName);
                        zos.putNextEntry(entry);

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = is.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                        zos.closeEntry();
                    }
                } catch (Exception e) {
                    System.out.println("Gagal mendownload foto: " + photo.getImageUrl());
                }
            }
            zos.finish();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Gagal membuat file ZIP", e);
        }
    }
}