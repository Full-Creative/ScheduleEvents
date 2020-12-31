package controller;

import java.util.Map.Entry;

import datastore.DataBaseException;
import datastore.EventsDB;
import model.Event;
import model.ParticipantDetails;
import java.util.*;

public class EventServiceImp extends SortHelper implements EventService {
	public EventsDB eventDB;

	public EventServiceImp() {
		eventDB = new EventsDB();
	}

	@Override
	public List<Event> addEvents(List<Event> eventDetails) {
		if (eventDetails == null || eventDetails.isEmpty())
			throw new IllegalArgumentException("Event Details not available");
		boolean inserted = false;
		try {
			inserted = eventDB.setEvents(eventDetails);
			if (!inserted)
				throw new DataBaseException("Not inserted");
		} catch (DataBaseException e) {
			System.out.println(e.getMessage());
		}
		return eventDetails;
	}

	@Override
	public Event modifyEvent(Event event) throws DataBaseException {
		if (retrieveById(event.getEventID()) == null || event.getEventID() < 0)
			throw new IllegalArgumentException("Unavailable id");
		boolean modified = false;
		//try {
			modified = eventDB.setEvent(event);
			//if (modified)
			//	return event;
			if(!modified)
				throw new DataBaseException("Not modified");
//		} catch (DataBaseException e) {
//			System.out.println(e.getMessage());
//		}
		//return retrieveById(event.getEventID());
			return event;
	}

	@Override
	public void deleteEvents(List<Integer> id) {
		for (Integer i : id)
			if (retrieveById(i) == null)
				throw new IllegalArgumentException("Event Id not available");
		eventDB.removeEvents(id);
	}

	@Override
	public void addParticipants(int id, Set<ParticipantDetails> participants) {
		if (participants == null || participants.isEmpty())
			throw new IllegalArgumentException("Participant Details not available");
		if (eventDB.getEvents().get(id) == null || id < 0)
			throw new IllegalArgumentException("No such event");

		eventDB.getEvents().get(id).setParticipants(participants);
		//return event.get(id)
	}

	@Override
	public Event retrieveById(int id) {
		Map<Integer, Event> events = eventDB.getEvents();
		if (events.get(id) == null)
			throw new IllegalArgumentException("No such event");
		return events.get(id);

	}

	@Override
	public Map<Integer, Event> retrieveByTimeRange(long start, long end) {
		Map<Integer, Event> events = eventDB.getEvents();

		Map<Integer, Event> eventsList = new HashMap<Integer, Event>();
		for (Entry<Integer, Event> entry : events.entrySet()) {
			if (entry.getValue().getEventTime() >= start && entry.getValue().getEventTime() <= end)
				eventsList.put(entry.getKey(), entry.getValue());
		}
		return eventsList;
	}

	public HashMap<Integer, Event> sortByParticipantCount() {
		Map<Integer, Event> events = eventDB.getEvents();
		HashMap<Integer, Event> sortedEvents = sortByParticipantCountHelper(events);
		return sortedEvents;
	}

	public HashMap<Integer, Event> sortByEventDuration() {
		Map<Integer, Event> events = eventDB.getEvents();
		HashMap<Integer, Event> sortedEvents = sortByEventDurationHelper(events);
		return sortedEvents;
	}

	public HashMap<Integer, Event> sortByEventCreated() {
		Map<Integer, Event> events = eventDB.getEvents();
		HashMap<Integer, Event> sortedEvents = sortByEventCreatedHelper(events);
		return sortedEvents;
	}

	public HashMap<Integer, Event> sortByEventTime() {
		Map<Integer, Event> events = eventDB.getEvents();
		HashMap<Integer, Event> sortedEvents = sortByEventTimeHelper(events);
		return sortedEvents;
	}
}
