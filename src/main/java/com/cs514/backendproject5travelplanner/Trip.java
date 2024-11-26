package com.cs514.backendproject5travelplanner;

public class Trip {
    private String destination;
    private int duration; // Duration in days
    private String month; // Month of the trip

    // Constructor
    public Trip(String destination, int duration, String month) {
        this.destination = destination;
        this.duration = duration;
        this.month = month;
    }

    // Getters and Setters
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
}
