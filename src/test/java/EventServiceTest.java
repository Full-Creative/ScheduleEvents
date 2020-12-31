import static org.junit.Assert.*;
import org.junit.Test;
import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import model.Event;
import model.ParticipantDetails;
import service.event.EventServiceImp;

public class EventServiceTest {
	public EventServiceImp eventService = new EventServiceImp();

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent() {
		Event eventDetails = new Event();
		eventDetails.setEventTime(0);
		eventService.addEvent(eventDetails);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent1() {
		Event eventDetails = new Event();
		eventDetails.setEventDuration(0);
		assertEquals(eventDetails, eventService.addEvent(eventDetails));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent2() {
		Event eventDetails = new Event();
		eventDetails.setEventID(null);
		assertEquals(eventDetails, eventService.addEvent(eventDetails));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent3() {
		assertEquals(null, eventService.addEvent(null));
	}

	@Test
	public void testModifyEvent() {
		try {
			Event eventDetails = new Event();
			eventDetails.setEventID(null);
			assertEquals(eventDetails, eventService.modifyEvent(eventDetails));
		} catch (IllegalArgumentException e) {
			assertEquals("Exception message must be correct", "Invalid ID", e.getMessage());
		} catch (EntityNotFoundException e) {
			assertEquals("Exception message must be correct", "Event not found", e.getMessage());
		} catch (DataBaseException e) {
			assertEquals("Exception message must be correct", "Not modified", e.getMessage());
		}
	}

	public void testRemoveEvent() {
		try {
			eventService.removeEvent(null);
		} catch (EntityNotFoundException e) {
			assertEquals("Exception message must be correct", "Event not found", e.getMessage());
		} catch (IllegalArgumentException e) {
			assertEquals("Exception message must be correct", "Event not available", e.getMessage());
		}
	}

	@Test
	public void testRetrieveById() {
		try {
			assertEquals(new Event(), eventService.retrieveById(null));
		} catch (EntityNotFoundException e) {
			assertEquals("Exception message must be correct", "Event not found", e.getMessage());
		} catch (IllegalArgumentException e) {
			assertEquals("Exception message must be correct", "Invalid ID", e.getMessage());
		}

	}

	@Test
	public void testAddParticipant() {
		ParticipantDetails participant = new ParticipantDetails();
		participant.setEmail(null);
		try {
			assertEquals(participant, eventService.addParticipant(participant));
		} catch (EntityNotFoundException e) {
			assertEquals("Exception message must be correct", "Event not found", e.getMessage());
		} catch (IllegalArgumentException e) {
			assertEquals("Exception message must be correct", "Participant Details not available", e.getMessage());
		}

	}

	@Test
	public void testAddParticipant1() {
		ParticipantDetails participant = new ParticipantDetails();
		participant.setName(null);
		try {
			assertEquals(participant, eventService.addParticipant(participant));
		} catch (EntityNotFoundException e) {
			assertEquals("Exception message must be correct", "Event not found", e.getMessage());
		} catch (IllegalArgumentException e) {
			assertEquals("Exception message must be correct", "Participant Details not available", e.getMessage());
		}

	}

	@Test
	public void testAddParticipant2() {
		try {
			assertEquals(null, eventService.addParticipant(null));
		} catch (EntityNotFoundException e) {
			assertEquals("Exception message must be correct", "Event not found", e.getMessage());
		} catch (IllegalArgumentException e) {
			assertEquals("Exception message must be correct", "Participant Details not available", e.getMessage());
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveByTimeRange() {
		assertEquals(null, eventService.retrieveByTimeRange(-1, 0));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveByTimeRange1() {
			assertEquals(null, eventService.retrieveByTimeRange(0, -1));
	}

}
