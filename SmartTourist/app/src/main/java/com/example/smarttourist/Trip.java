package com.example.smarttourist;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Trip {
    private String Name;
    private String Place;
    private String Duration;
    private Long Price;
    private byte[] Image;
    private String Type;
    private String Description;
    private Date StartDate;
    private Date endDate;



    private String AgentId;

    public Trip(String name, String place, String duration, Long price, byte[] image, String type, String description, Date startDate, Date endDate, String agentId) {
        Name = name;
        Place = place;
        Duration = duration;
        Price = price;
        Image = image;
        Type = type;
        Description = description;
        StartDate = startDate;
        this.endDate = endDate;
        AgentId = agentId;
    }  public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "Name='" + Name + '\'' +
                ", Place='" + Place + '\'' +
                ", Duration='" + Duration + '\'' +
                ", Price=" + Price +
                ", Type='" + Type + '\'' +
                ", Description='" + Description + '\'' +
//                ", MainImage='" + MainImage + '\'' +
                ", StartDate=" + StartDate +
                ", endDate=" + endDate +
                ", images=" + Arrays.toString(images) +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;
        Trip trip = (Trip) o;
        return Objects.equals(getName(), trip.getName()) && Objects.equals(getPlace(), trip.getPlace()) && Objects.equals(getDuration(), trip.getDuration()) && Objects.equals(getPrice(), trip.getPrice()) && Objects.equals(getType(), trip.getType()) && Objects.equals(getDescription(), trip.getDescription()) && Arrays.equals(getImages(), trip.getImages());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName(), getPlace(), getDuration(), getPrice(), getType(), getDescription());
        result = 31 * result + Arrays.hashCode(getImages());
        return result;
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

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
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

//    public String getHealthCondition() {
//        return HealthCondition;
//    }

//    public void setHealthCondition(String healthCondition) {
//        HealthCondition = healthCondition;
//    }



    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Trip() {
    }

    private String[] images;


}




