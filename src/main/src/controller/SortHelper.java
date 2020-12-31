package controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Event;

public class SortHelper {
	
	protected HashMap<Integer, Event> sortByParticipantCountHelper(Map<Integer, Event> events) {
		List<Map.Entry<Integer, Event>> list = new LinkedList<Map.Entry<Integer, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Event>>() {
			public int compare(Map.Entry<Integer, Event> o1, Map.Entry<Integer, Event> o2) {
				if(o1.getValue().getParticipants() !=null && o2.getValue().getParticipants()!=null)
				return (Integer.valueOf(o1.getValue().getParticipants().size()))
						.compareTo(Integer.valueOf(o2.getValue().getParticipants().size()));
				else return -1;
			}
		});

		HashMap<Integer, Event> temp = new LinkedHashMap<Integer, Event>();
		for (Map.Entry<Integer, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}

		return temp;
	}
	protected HashMap<Integer, Event> sortByEventDurationHelper(Map<Integer, Event> events) {
		List<Map.Entry<Integer, Event>> list = new LinkedList<Map.Entry<Integer, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Event>>() {
			public int compare(Map.Entry<Integer, Event> o1, Map.Entry<Integer, Event> o2) {
		if( o1.getValue().getEventDuration()==(o2.getValue().getEventDuration()))
			return 0;
		else if( o1.getValue().getEventDuration()>(o2.getValue().getEventDuration()))
		return 1;
		else
			return -1;
			}
		});

		HashMap<Integer, Event> temp = new LinkedHashMap<Integer, Event>();
		for (Map.Entry<Integer, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}
		return temp;
	}
	protected HashMap<Integer, Event> sortByEventCreatedHelper(Map<Integer, Event> events) {
		List<Map.Entry<Integer, Event>> list = new LinkedList<Map.Entry<Integer, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Event>>() {
			public int compare(Map.Entry<Integer, Event> o1, Map.Entry<Integer, Event> o2) {
		if( o1.getValue().getEventCreatedTime()==(o2.getValue().getEventCreatedTime()))
			return 0;
		else if( o1.getValue().getEventCreatedTime()>(o2.getValue().getEventCreatedTime()))
		return 1;
		else
			return -1;
			}
		});

		HashMap<Integer, Event> temp = new LinkedHashMap<Integer, Event>();
		for (Map.Entry<Integer, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}
		return temp;
	}
	public HashMap<Integer, Event> sortByEventTimeHelper(Map<Integer, Event> events) {
		List<Map.Entry<Integer, Event>> list = new LinkedList<Map.Entry<Integer, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Event>>() {
			public int compare(Map.Entry<Integer, Event> o1, Map.Entry<Integer, Event> o2) {
		if( o1.getValue().getEventTime()==(o2.getValue().getEventTime()))
			return 0;
		else if( o1.getValue().getEventTime()>(o2.getValue().getEventTime()))
		return 1;
		else
			return -1;
			}
		});

		HashMap<Integer, Event> temp = new LinkedHashMap<Integer, Event>();
		for (Map.Entry<Integer, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}
		return temp;
	}

}

