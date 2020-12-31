package datastore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Event;

public class EventsDB {
	private Map<Integer, Event> events ;

	public EventsDB(){
		events= new HashMap<Integer, Event>();
	}
	public Map<Integer, Event> getEvents() {
		return  events;
	}

	public boolean setEvents(List<Event> events) {
		//this.events = events;
		for(int i=0;i<events.size();i++) {
		this.events.put(events.get(i).getEventID(), events.get(i));
		}
		//this.events.putAll(events);
		return true;
	}
	public boolean setEvent(Event event) {
		this.events.put(event.getEventID(), event);
		return true;
	}
//	public boolean removeEvent(int id) {
//		this.events.remove(id);
//	return true;	
//	}
	public boolean removeEvents(List<Integer> id)
	{
		for(Integer i:id)
			this.events.remove(i);
		return true;
	}
	

}
