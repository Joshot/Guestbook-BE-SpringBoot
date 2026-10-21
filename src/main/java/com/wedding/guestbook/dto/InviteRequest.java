package com.wedding.guestbook.dto;

public class InviteRequest {
    private Long eventId;
    private Long inviterId; 
    private String inviteeUsername;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getInviterId() {
		return inviterId;
	}
	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}
	public String getInviteeUsername() {
		return inviteeUsername;
	}
	public void setInviteeUsername(String inviteeUsername) {
		this.inviteeUsername = inviteeUsername;
	} 

}