package dev.nesi.models;

public class Lodging {
    private Integer numberOfRooms;
    private Integer numberOfDays;
    private Double averageCost;

    public Lodging(){
        this.numberOfDays = 1;
        this.numberOfRooms = 1;
        this.averageCost = 0D;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

}
