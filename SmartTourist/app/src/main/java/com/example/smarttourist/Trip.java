package com.example.smarttourist;

import android.graphics.Bitmap;

import java.util.Date;

public class Trip {
    private String ID;

    private String Name;
    private String Place;
    private String Duration;
    private double Price;
    private String Type;
    private String Description;
    private Date StartDate;
    private Date endDate;
    private String AgentId;
private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Trip(String ID, String name, String place, String duration, double price, String type, String description, Date startDate, Date endDate, String agentId) {
        this.ID = ID;
        Name = name;
        Place = place;
        Duration = duration;
        Price = price;
        Type = type;
        Description = description;
        StartDate = startDate;
        this.endDate = endDate;
        AgentId = agentId;
    }

    public Trip() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Place='" + Place + '\'' +
                ", Duration='" + Duration + '\'' +
                ", Price=" + Price +
                ", Type='" + Type + '\'' +
                ", Description='" + Description + '\'' +
                ", StartDate=" + StartDate +
                ", endDate=" + endDate +
                ", AgentId='" + AgentId + '\'' +
                '}';
    }
}




