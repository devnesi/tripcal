package dev.nesi.models;


public class Gasoline {
    private Double numberOfKm;
    private Double kmPerLiter;
    private Double literCost;
    private Double numberOfVehicles;

    public Gasoline() {
        this.numberOfKm = 0D;
        this.kmPerLiter = 0D;
        this.literCost = 0D;
        this.numberOfVehicles = 1D;
    }

    public Double getNumberOfKm() {
        return numberOfKm;
    }

    public void setNumberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public Double getKmPerLiter() {
        return kmPerLiter;
    }

    public void setKmPerLiter(Double kmPerLiter) {
        this.kmPerLiter = kmPerLiter;
    }

    public Double getLiterCost() {
        return literCost;
    }

    public void setLiterCost(Double literCost) {
        this.literCost = literCost;
    }

    public Double getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(Double numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }
}
