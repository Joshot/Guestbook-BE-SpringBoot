package com.wedding.guestbook.dto;

public class GuestbookRequest {
    private String guestName;
    private String imageUrl;
    private String wishMessage;
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
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


    
}