package datastore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import model.Event;
import model.ParticipantDetails;

import com.fasterxml.uuid.Generators;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.util.UUID;
//import com.google.cloud.Date;

public class EventsDB {
	private DatastoreService datastore;

	public EventsDB() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public Event getEvent(String id) throws EntityNotFoundException {
		Event event = new Event();
		Key eventKey = KeyFactory.createKey("Event", id);
		Entity e = datastore.get(eventKey);
		event = entityToObject(e);
		return event;
	}

	public boolean setEvent(Event event) {
		String id = Generators.timeBasedGenerator().generate().toString();
		Key key = KeyFactory.createKey("Event", id);
		Entity eventEntity = new Entity(key);
		eventEntity.setProperty("EventID", id);
		eventEntity.setProperty("EventTitle", event.getEventTitle());
		eventEntity.setProperty("EventDuration", event.getEventDuration());
		eventEntity.setProperty("EventTime", event.getEventTime());
		eventEntity.setProperty("EventCreatedTime", System.currentTimeMillis());

		// ObjectifyService.ofy().save().entity(event).now();
//		ObjectifyService.init();
//		ObjectifyService.begin();
//		ObjectifyService.register(Event.class);
//		ObjectifyService.register(ParticipantDetails.class);
//		ofy().save().entity(event).now();
		datastore.put(eventEntity);
		return true;
	}

	public boolean updateEvent(Event event) {
		try {
			Entity eventEntity = datastore.get(KeyFactory.createKey("Event", event.getEventID()));
			if (eventEntity != null) {
				if (event.getEventTitle() != null)
					eventEntity.setProperty("EventTitle", event.getEventTitle());
				if (event.getEventDuration() != 0)
					eventEntity.setProperty("EventDuration", event.getEventDuration());
				if (event.getEventTime() != 0)
					eventEntity.setProperty("EventTime", event.getEventTime());
			}

			datastore.put(eventEntity);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean deleteEvent(String id) throws EntityNotFoundException {
		Key eventKey = KeyFactory.createKey("Event", id);
		Entity eventEntity = datastore.get(eventKey);
		if (eventEntity == null)
			throw new IllegalArgumentException("Event not available");
		datastore.delete(eventKey);
		return true;
	}

	public boolean createParticipant(ParticipantDetails participant) {
		String id = Generators.timeBasedGenerator().generate().toString();
		Key key = KeyFactory.createKey("Participant", id);

		Entity participantEntity = new Entity(key);
		participantEntity.setProperty("ParticipantID", id);

		participantEntity.setProperty("ParticipantName", participant.getName());
		participantEntity.setProperty("ParticipantEmail", participant.getEmail());
		participantEntity.setProperty("TimeZone", participant.getTimeZone());
		datastore.put(participantEntity);
		return true;
	}

	public boolean addParticipant(ParticipantDetails participant) {

		List<String> participantIDinEvent = new ArrayList<String>();
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Filter ids = new Query.FilterPredicate("ParticipantEmail", FilterOperator.EQUAL, participant.getEmail());
			Query q = new Query("Participant").setFilter(ids);
			PreparedQuery pq = datastore.prepare(q);
			Entity participantEntity = pq.asSingleEntity();
			if (participantEntity == null)
				throw new EntityNotFoundException(null);

			String participantID = (String) participantEntity.getProperty("ParticipantID");

			Filter event = new Query.FilterPredicate("EventID", FilterOperator.EQUAL, participant.getEventID());
			q = new Query("Event").setFilter(event);
			pq = datastore.prepare(q);
			Entity eventEntity = pq.asSingleEntity();
			if (eventEntity.getProperty("Email") != null) {
				participantIDinEvent = new Gson().fromJson(eventEntity.getProperty("ParticipantID").toString(), List.class);
			}

			if (!participantIDinEvent.contains(participantID))
				participantIDinEvent.add(participantID);

			eventEntity.setProperty("ParticipantKey", participantIDinEvent);
			datastore.put(eventEntity);

		} catch (EntityNotFoundException e) {
			createParticipant(participant);
			addParticipant(participant);
			e.printStackTrace();
		}
		return true;
	}

	public boolean updateParticipant(ParticipantDetails participant) {
		try {
			Entity participantEntity = datastore.get(KeyFactory.createKey("Participant", participant.getEmail()));
			if (participantEntity != null) {
				if (participant.getName() != null)
					participantEntity.setProperty("ParticipantName", participant.getName());
				if (participant.getTimeZone() != null)
					participantEntity.setProperty("TimeZone", participant.getTimeZone());
			}

			datastore.put(participantEntity);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean removeParticipant(String id) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter ids = new Query.FilterPredicate("ParticipantEmail", FilterOperator.EQUAL, id);
		Query q = new Query("Participant").setFilter(ids);
		PreparedQuery pq = datastore.prepare(q);

		List<Key> keys = new ArrayList<Key>();
		for (Entity e : pq.asList(FetchOptions.Builder.withLimit(1000))) {

			keys.add(e.getKey());

		}
		datastore.delete(keys);

		return true;
	}

	public Event entityToObject(Entity entity) {

		Event event = new Event();
		event.setEventID((String) entity.getProperty("EventID"));
		event.setEventTitle(String.valueOf(entity.getProperty("EventTitle")));
		event.setEventDuration((long) entity.getProperty("EventDuration"));
		event.setEventCreatedTime((long) entity.getProperty("EventCreatedTime"));
		event.setEventTime((long) entity.getProperty("EventTime"));
		if (entity.getProperty("ParticipantKey") != null) {
			Set<String> ar = new Gson().fromJson(entity.getProperty("ParticipantKey").toString(), HashSet.class);
			event.setParticipantEmail(ar);
		}
		return event;
	}

	public List<Entity> sortQueryAsc(String property) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Event").addSort(property, SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}

	public List<Event> sortByDuration() {
		List<Event> events = new ArrayList<Event>();
		for (Entity e : sortQueryAsc("EventDuration")) {
			events.add(entityToObject(e));
		}
		return events;
	}

	public List<Event> sortByCreatedTime() {
		List<Event> events = new ArrayList<Event>();
		for (Entity e : sortQueryAsc("EventCreatedTime")) {
			events.add(entityToObject(e));
		}
		return events;
	}

	public List<Event> sortByStartTime() {
		List<Event> events = new ArrayList<Event>();
		for (Entity e : sortQueryAsc("EventTime")) {
			events.add(entityToObject(e));
		}
		return events;
	}

	public List<Event> retrieveByTimeRange(long start, long end) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter greaterThan = new Query.FilterPredicate("EventTime", FilterOperator.GREATER_THAN_OR_EQUAL, start);
		Filter lessThan = new Query.FilterPredicate("EventTime", FilterOperator.LESS_THAN_OR_EQUAL, end);
		Filter filterTime = CompositeFilterOperator.and(lessThan, greaterThan);
		Query q = new Query("Event").setFilter(filterTime);
		PreparedQuery pq = datastore.prepare(q);

		List<Event> events = new ArrayList<Event>();
		for (Entity e : pq.asList(FetchOptions.Builder.withDefaults())) {
			events.add(entityToObject(e));
		}
		return events;
	}

	public List<Event> retrieveByDatesortByParticipantCount(String date) throws ParseException {
		List<Event> events = retrieveByDate(date);
		Collections.sort(events, new Comparator<Event>() {
			public int compare(Event s1, Event s2) {
				return s2.getParticipantEmail().size() - s1.getParticipantEmail().size();
			}
		});

		return events;
	}

	public List<Event> retrieveByDate(String date) throws ParseException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Calendar cal = Calendar.getInstance();
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		cal.setTime(date1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);

		long start = cal.getTimeInMillis();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);

		long end = cal.getTimeInMillis();

		Filter greaterThan = new Query.FilterPredicate("EventTime", FilterOperator.GREATER_THAN_OR_EQUAL, start);
		Filter lessThan = new Query.FilterPredicate("EventTime", FilterOperator.LESS_THAN_OR_EQUAL, end);
		Filter filterDate = CompositeFilterOperator.and(greaterThan, lessThan);
		Query q = new Query("Event").setFilter(filterDate);
		q.addSort("EventTime", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);

		List<Event> events = new ArrayList<Event>();
		for (Entity e : pq.asList(FetchOptions.Builder.withDefaults())) {
			events.add(entityToObject(e));
		}
		return events;
	}

	public List<Event> retrieveEventByEmail(String email) throws EntityNotFoundException, ParseException {
		Query participantQuery=new Query("Participant");
		Filter participantIDFilter=new Query.FilterPredicate("ParticipantEmail",FilterOperator.EQUAL,email);
		participantQuery.setFilter(participantIDFilter);
		PreparedQuery pq=datastore.prepare(participantQuery);
		Entity participantEntity=pq.asSingleEntity();
		String participantID=(String) participantEntity.getProperty("ParticipantID");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Event");
		Filter filterEmail = new Query.FilterPredicate("ParticipantKey", FilterOperator.EQUAL, participantID);
		q.setFilter(filterEmail);
		q.addSort("EventID", SortDirection.ASCENDING);

		 pq = datastore.prepare(q);
		List<Event> events = new ArrayList<Event>();

	//	Key participantKey = KeyFactory.createKey("Participant", email);
//		Entity participant = datastore.get(participantKey);
		String participantTimeZone = (String) participantEntity.getProperty("TimeZone");
		TimeZone zoneID = TimeZone.getTimeZone(participantTimeZone);

		for (Entity e : pq.asList(FetchOptions.Builder.withDefaults())) {
			Event event = getEvent(e.getProperty("EventID").toString());
			if (participantTimeZone != null) {
				Calendar convertedTime = Calendar.getInstance();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
				sdf.setTimeZone(zoneID);
				convertedTime.setTimeZone(zoneID);

				String res = sdf.format(event.getEventTime());
				convertedTime.setTime(sdf.parse(res));
				event.setEventTime(convertedTime.getTimeInMillis());
				System.out.println(res);
				res = sdf.format(event.getEventCreatedTime());
				convertedTime.setTime(sdf.parse(res));
				event.setEventCreatedTime(convertedTime.getTimeInMillis());

				System.out.println(res);
			}
			events.add(event);
		}
		if (events.size() == 0)
			throw new IllegalArgumentException();
		return events;
	}
}
