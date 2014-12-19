package com.flhs.utils;

public class eventobject{
	public String Date;
	public String[] eventsDesc = new String[6]; //Change if there somehow is more than 6 events......
	public eventobject () {
		for(int e = 0; e < 6; e++) { //Remember to change this too if there is somehow more than 6 events....
			eventsDesc[e] = "";
		}
	}


	
	public void setDate(String date) {
		Date = date;
	}
	public void setEventsDesc(int index, String eventstring) {
		eventsDesc[index] = eventstring;
	}
}	
