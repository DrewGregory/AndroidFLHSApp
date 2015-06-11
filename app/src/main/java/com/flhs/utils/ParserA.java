/*
 * Author:Drew Gregory,
 * for: parseEventsToday(), printSportsToday(), parseSchedules() 
 */
package com.flhs.utils;


import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserA {
    final static int MIDDLE_LUNCH = 1;
    final static int LATE_LUNCH = 2;

    public static String integerMonthToString(int month) {
        String monthString;
        switch (month) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    public static ArrayList<eventobject> parseEventsToday() {
        ArrayList<eventobject> date = new ArrayList<eventobject>();
        Document Magic;
        try {
            Magic = Jsoup.connect(new Formatter().getFLHSCalendarURL()).get();
            Elements tableDateBox = Magic.getElementsByAttributeValue("style", "background-color: #ffffcc");
            Elements dateBoxText = tableDateBox.get(0).child(1).child(0).child(1).child(1).children();
            for (int eventcalindex = 0; eventcalindex < dateBoxText.size(); eventcalindex++) {
                Element todayevent = dateBoxText.get(eventcalindex);
                String atags = todayevent.getElementsByTag("a").text();
                String aTagsPlusTime = todayevent.text();
                String Time = "";
                if (!atags.equals("") && !aTagsPlusTime.equals("")) {
                    Time = aTagsPlusTime.substring(atags.length(), aTagsPlusTime.length());
                }
                if (!atags.equals("")) {
                    if (!atags.startsWith("FLMS") && !atags.startsWith("BHES") && !atags.startsWith("PRES") && !atags.startsWith("BVES") && !atags.startsWith("MKES") && !atags.startsWith("WPES")) {
                        eventobject tempEventObject = new eventobject();
                        tempEventObject.setDate(Time);
                        tempEventObject.setEventsDesc(0, atags);
                        date.add(tempEventObject);
                    }
                }
            }
        } catch (IOException e) {
            date = null;
        }

        return date;

    }

    public static ArrayList<SportEvent> printSportsToday() {
        ArrayList<SportEvent> SportsNewsEvents;
        String url = "http://sportspak.swboces.org/sportspak/oecgi3.exe/O4W_SPAKONLINE_HOME"; //Day, Month, Year, 0 if I want today's..... Change 13 to 0 after testing....
        try {
            Document SportsFormatToPrintWebpage = Jsoup.connect(url).get();
            Element spanResults = SportsFormatToPrintWebpage.getElementById("DAILY_EVENTS");
            SportsNewsEvents = new ArrayList<SportEvent>();
            if (spanResults != null) {
                Elements EventRows = spanResults.child(1).children();
                for (int EventRowIndex = 1; EventRowIndex < EventRows.size(); EventRowIndex++) { //I put it at 1 because the first row is just the header...... so I skipped 0....
                    Element EventRow = EventRows.get(EventRowIndex);
                    if (EventRow.child(4).text().contains("FOX LANE HS") || EventRow.child(3).text().contains("FOX LANE HS")) {
                        SportEvent Game = new SportEvent();
                        /*
				 		TR Help:
				 		child(0) Game Time
				 		child(1) Sport
				 		child(2) Level
				 		child(3) Home
				 		child(4) Visitor
				 		child(5) Type
				 		child(6) Facility 
						 */
                        Game.setSport(EventRow.child(2).text() + " " + EventRow.child(1).text());
                        Game.setVisitingTeam(EventRow.child(4).text());
                        Game.setHomeTeam(EventRow.child(3).text());
                        Game.setTime(EventRow.child(0).text());
                        SportsNewsEvents.add(Game);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            SportsNewsEvents = null;
        }
        return SportsNewsEvents;
    }

    public static String[] setLunchOrder(String[] Lunch1Schedule, int lunchType) {
        String[] ParsedSchedule = Lunch1Schedule;
        if (Lunch1Schedule.length == 7) {
            String Lunch = Lunch1Schedule[2];
            String FirstClass = Lunch1Schedule[3];
            String SecondClass = Lunch1Schedule[4];
            if (lunchType == MIDDLE_LUNCH) {
                ParsedSchedule[2] = FirstClass;
                ParsedSchedule[3] = Lunch;
                ParsedSchedule[4] = SecondClass;
            }
            if (lunchType == LATE_LUNCH) {
                ParsedSchedule[2] = FirstClass;
                ParsedSchedule[3] = SecondClass;
                ParsedSchedule[4] = Lunch;
            }
        }
        if (Lunch1Schedule.length == 9) {

            String Lunch = Lunch1Schedule[3];
            String FirstClass = Lunch1Schedule[4];
            String SecondClass = Lunch1Schedule[5];
            if (lunchType == MIDDLE_LUNCH) {
                ParsedSchedule[3] = FirstClass;
                ParsedSchedule[4] = Lunch;
                ParsedSchedule[5] = SecondClass;
            }
            if (lunchType == LATE_LUNCH) {
                ParsedSchedule[3] = FirstClass;
                ParsedSchedule[4] = SecondClass;
                ParsedSchedule[5] = Lunch;
            }
        }
        return ParsedSchedule;
    }

    public static String parseNumToDay(int day) {
        switch (day) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "1";
            case 7:
                return "2";
            case 8:
                return "3";
            case 9:
                return "4";
            case 10:
                return "5";
            case 11:
                return "Adv E";
            case 12:
                return "Adv 5";
            case 13:
                return "Collab E";
            case 14:
                return "Collab 5";
            default:
                return "Unknown";
        }
    }
}
