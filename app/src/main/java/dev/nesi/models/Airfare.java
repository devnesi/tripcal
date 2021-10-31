package dev.nesi.models;

import com.google.firebase.database.Exclude;

public class Airfare {
    private Integer numberOfPeople;
    private Double vehicleRent;
    private Double costPerPerson;

    public Airfare(){
        this.numberOfPeople = 1;
        this.vehicleRent = 0D;
        this.costPerPerson = 0D;
    }

    public Double getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(Double costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    @Exclude
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    @Exclude
    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Double getVehicleRent() {
        return vehicleRent;
    }

    public void setVehicleRent(Double vehicleRent) {
        this.vehicleRent = vehicleRent;
    }

}
