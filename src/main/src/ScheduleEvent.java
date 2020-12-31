import java.util.Arrays;

import datastore.DataBaseException;
import controller.EventServiceImp;
import datastore.DataBaseException;
import generator.DataGenerator;

public class ScheduleEvent {
	public static void main(String[] args) {
		EventServiceImp eventService = new EventServiceImp();
		DataGenerator generator = new DataGenerator();
		PrintEvent show = new PrintEvent(eventService);
		
		try {
			eventService.addEvents(generator.generateEvents(4));
			eventService.addParticipants(1, generator.generateParticipants(3));
			eventService.addParticipants(2, generator.generateParticipants(4));
			eventService.addParticipants(3, generator.generateParticipants(1));
			eventService.addEvents(generator.generateEvents(1));
			eventService.addParticipants(4, generator.generateParticipants(1));
			show.listAll();
	
			eventService.deleteEvents(Arrays.asList(1));
			System.out.println("after deleting event id 1");
			show.listAll();
			eventService.modifyEvent(generator.generateEvent());		
			
				
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (DataBaseException e) {
			System.out.println(e.getMessage());
		}
		

		
	//	eventService.modifyEvent(generator.generateEvent());		
		
	
		show.displayByTimeRange();
		show.displaySortBasedOnParticipantCount();
		show.displaySortBasedOnDuration();
		show.displaySortBasedOnCreatedTime();
		show.displaySortBasedOnTime();

		/*
		 * String myDate = "2014/10/29 19:10:45"; sdf = new
		 * SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); Date date; long millis = 0; try {
		 * date = sdf.parse(myDate); millis = date.getTime(); } catch (ParseException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } long res =
		 * (1414590045000L) - (1414676445000L); System.out.println("\n" + res); //
		 * System.out.println("30/10 "+ "1414676445000"); //
		 * System.out.println(3600000*3);
		 */

	}

}
