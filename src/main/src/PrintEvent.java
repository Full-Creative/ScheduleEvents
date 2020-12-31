import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import controller.EventServiceImp;
import datastore.EventsDB;
import model.Event;
import model.ParticipantDetails;

public class PrintEvent {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	EventServiceImp eventService;
//	EventsDB eventsDB;

	public PrintEvent(EventServiceImp eventService) {
		this.eventService = eventService;
		// this.eventsDB=new EventsDB();
	}

	public void listAll() {
		System.out.println("The list of events are");
		for (Entry<Integer, Event> entry : eventService.eventDB.getEvents().entrySet()) {
			int participants = (entry.getValue().getParticipants() == null) ? 0
					: entry.getValue().getParticipants().size();
			System.out.print("Event ID = " + entry.getValue().getEventID() + ", " + " Event Duration "
					+ entry.getValue().getEventDuration() / 3600000 + "hrs" + "  Event Time: "
					+ (sdf.format(new Date(entry.getValue().getEventTime()))) + "  Event Created Time:  "
					+ (sdf.format(new Date(entry.getValue().getEventCreatedTime()))) + " Number of participants: "
					+ participants + " Event name " + entry.getValue().getEventTitle() + "\n");
		}
	}

	public void displayEvent(int id) {
		System.out.println("Event Details : ");
		Event event = eventService.retrieveById(id);
		int participants = (event.getParticipants() == null) ? 0 : event.getParticipants().size();
		System.out.print(
				"Event ID = " + event.getEventID() + ", " + " Event Duration " + event.getEventDuration() / 3600000
						+ "hrs" + "  Event Time: " + (sdf.format(new Date(event.getEventTime())))
						+ "  Event Created Time:  " + (sdf.format(new Date(event.getEventCreatedTime())))
						+ " Number of participants: " + participants + " Event name " + event.getEventTitle() + "\n");

	}

	public void displayByTimeRange() {
		System.out.print("\nRetrieve by time range between " + sdf.format(1414330846000L) + " and "
				+ sdf.format(1414590044999L));
		for (Entry<Integer, Event> entry : eventService.retrieveByTimeRange(1414330846000L, 1414590044999L).entrySet())
			System.out.print(
					"\nEvent ID = " + entry.getKey() + ", " + (sdf.format(new Date(entry.getValue().getEventTime()))));

	}

	public void displaySortBasedOnParticipantCount() {
		System.out.print("\n\nSort based on participant count");
		for (Entry<Integer, Event> entry : eventService.sortByParticipantCount().entrySet()) {
			System.out.print("\nEvent ID = " + entry.getKey() + ",");
			if (entry.getValue().getParticipants() != null)
				for (ParticipantDetails p : entry.getValue().getParticipants()) {
					System.out.print(p.getEmail() + " ");
				}
		}
	}

	public void displaySortBasedOnDuration() {
		System.out.print("\n\nSort based on Event Duration");
		for (Entry<Integer, Event> entry : eventService.sortByEventDuration().entrySet()) {
			System.out.print("\nEvent ID = " + entry.getKey() + ", Event Duration "
					+ entry.getValue().getEventDuration() / 3600000 + "hrs");
		}
	}

	public void displaySortBasedOnCreatedTime() {
		System.out.print("\n\nSort based on  Event Created Time");
		for (Entry<Integer, Event> entry : eventService.sortByEventCreated().entrySet()) {
			System.out.print("\nEvent ID = " + entry.getKey() + ",Event Created Time "
					+ (sdf.format(new Date(entry.getValue().getEventCreatedTime()))));
		}
	}

	public void displaySortBasedOnTime() {
		System.out.print("\n\nSort based on  Event Time");
		for (Entry<Integer, Event> entry : eventService.sortByEventTime().entrySet()) {
			System.out.print("\nEvent ID = " + entry.getKey() + ", Event Time  "
					+ (sdf.format(new Date(entry.getValue().getEventTime()))));
		}
	}
}
