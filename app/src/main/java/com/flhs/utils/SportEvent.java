package com.flhs.utils;

public class SportEvent {
    String Date;
    String Sport;
    String VisitingTeam;
    String HomeTeam;

    public String getDate() {
        return Date;
    }

    public String getSport() {
        return Sport;
    }

    public String getVisitingTeam() {
        return VisitingTeam;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public String getTime() {
        return Time;
    }

    String Time;


    public void setDate(String date) {
        Date = date;
    }

    public void setSport(String sport) {
        Sport = sport;
    }

    public void setVisitingTeam(String visitingTeam) {
        VisitingTeam = visitingTeam;
    }

    public void setHomeTeam(String homeTeam) {
        HomeTeam = homeTeam;
    }

    public void setTime(String time) {
        Time = time;
    }


}
