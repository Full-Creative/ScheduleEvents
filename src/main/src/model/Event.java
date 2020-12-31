package model;

import java.util.*;

public class Event {
	private int eventID;
	private String eventTitle;
	private long eventTime;
	private long eventCreatedTime;
	private long eventDuration;
	private Set<ParticipantDetails> participants;

	public long getEventTime() {
		return eventTime;
	}

	public void setEventTime(long eventTime) {
		this.eventTime = eventTime;
	}

	public long getEventCreatedTime() {
		return eventCreatedTime;
	}

	public void setEventCreatedTime(long eventCreatedTime) {
		this.eventCreatedTime = eventCreatedTime;
	}

	public long getEventDuration() {
		return eventDuration;
	}

	public void setEventDuration(long eventDuration) {
		this.eventDuration = eventDuration;
	}

	public Set<ParticipantDetails> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<ParticipantDetails> participants) {
		if (this.participants != null)
			this.participants.addAll(participants);
		else
			this.participants = participants;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (eventCreatedTime ^ (eventCreatedTime >>> 32));
		result = prime * result + (int) (eventDuration ^ (eventDuration >>> 32));
		
		result = prime * result + eventID;
		//System.out.println( eventID);
		
		result = prime * result + (int) (eventTime ^ (eventTime >>> 32));
		result = prime * result + ((eventTitle == null) ? 0 : eventTitle.hashCode());
		result = prime * result + ((participants == null) ? 0 : participants.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventCreatedTime != other.eventCreatedTime)
			return false;
		if (eventDuration != other.eventDuration)
			return false;
		if (eventID != other.eventID)
			return false;
		if (eventTime != other.eventTime)
			return false;
		if (eventTitle == null) {
			if (other.eventTitle != null)
				return false;
		} else if (!eventTitle.equals(other.eventTitle))
			return false;
		if (participants == null) {
			if (other.participants != null)
				return false;
		} else if (!participants.equals(other.participants))
			return false;
		return true;
	}


//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		if (obj instanceof Event) {
//			Event temp = (Event) obj;
//			if (this.eventID == temp.eventID && this.eventCreatedTime == temp.eventCreatedTime
//					&& this.eventDuration == temp.eventDuration && this.eventTime == temp.eventTime
//					&& this.eventTitle.equals(temp.eventTitle) && this.participants.equals(temp.participants))
//				return true;
//		}
//		return false;
//	}
//
//	@Override
//	public int hashCode() {
//		return (this.eventTitle.hashCode() + this.email.hashCode());
//	}

}
