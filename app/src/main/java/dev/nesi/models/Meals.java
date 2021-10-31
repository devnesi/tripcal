package dev.nesi.models;


import com.google.firebase.database.Exclude;

public class Meals {

    private Integer numberOfPeople;
    private Integer mealsPerDay;
    private Double mealEstimatedValue;
    private Integer numberOfDays;


    public Meals(){
        this.mealsPerDay = 1;
        this.mealEstimatedValue = 0D;
        this.numberOfDays = 1;

    }
    @Exclude
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    @Exclude
    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Integer getMealsPerDay() {
        return mealsPerDay;
    }

    public void setMealsPerDay(Integer mealsPerDay) {
        this.mealsPerDay = mealsPerDay;
    }

    public Double getMealEstimatedValue() {
        return mealEstimatedValue;
    }

    public void setMealEstimatedValue(Double mealEstimatedValue) {
        this.mealEstimatedValue = mealEstimatedValue;
    }

    @Exclude
    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    @Exclude
    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
