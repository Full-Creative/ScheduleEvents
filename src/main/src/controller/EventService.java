package controller;

import java.util.*;

import datastore.DataBaseException;
import model.Event;
import model.ParticipantDetails;

public interface EventService {
	public List<Event> addEvents(List<Event> eventDetails);

	public void addParticipants(int id,Set<ParticipantDetails> participants);

	public Event modifyEvent(Event event) throws DataBaseException;

	public void deleteEvents(List<Integer> id);

	public Event retrieveById(int id);

	public Map<Integer, Event> retrieveByTimeRange(long start, long end);
}
