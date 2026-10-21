package com.wedding.guestbook.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "guestbooks")
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @Column(name = "guest_ip", nullable = false)
    private String guestIp;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "wish_message", columnDefinition = "TEXT")
    private String wishMessage;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestIp() {
		return guestIp;
	}

	public void setGuestIp(String guestIp) {
		this.guestIp = guestIp;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getWishMessage() {
		return wishMessage;
	}

	public void setWishMessage(String wishMessage) {
		this.wishMessage = wishMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

    
}