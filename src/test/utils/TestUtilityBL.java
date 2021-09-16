


import java.util.Date;
import java.util.Vector;

import configuration.ConfigXML;
import domain.Event;

/**
 * Utilities to access Data Base
 * @author IS2
 *
 */
public class TestUtilityBL {
		private TestUtilityDA dbManagerTest;
 	
    
	   public TestUtilityBL()  {			
			System.out.println("Creating TestFacadeImplementation instance");
			ConfigXML.getInstance();
			dbManagerTest=new TestUtilityDA(); 
			dbManagerTest.close();
		}
		
		 
		public boolean removeEvent(Event ev) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeEvent(ev);
			dbManagerTest.close();
			return b;

		}
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			dbManagerTest.open();
			Event o=dbManagerTest.addEventWithQuestion(desc,d, question, qty);
			dbManagerTest.close();
			return o;

		}
		
		public Vector<Event> getEvents(Date date)  {
			dbManagerTest.open();
			Vector<Event>  events=dbManagerTest.getEvents(date);
			dbManagerTest.close();
			return events;
		}


}
