package dev.nesi.calculators.Meals;

import java.math.BigDecimal;

import dev.nesi.calculators.ICalculationService;
import dev.nesi.models.Meals;


public class MealsCalculationService implements ICalculationService {
    private BigDecimal numberOfPeople;
    private BigDecimal mealsPerDay;
    private BigDecimal mealEstimatedValue;
    private BigDecimal numberOfDays;

    public MealsCalculationService(
        final BigDecimal mealsPerDay,
        final BigDecimal numberOfPeople,
        final BigDecimal mealEstimatedValue,
        final BigDecimal numberOfDays
    ) {
        this.mealsPerDay = mealsPerDay;
        this.numberOfPeople = numberOfPeople;
        this.mealEstimatedValue = mealEstimatedValue;
        this.numberOfDays = numberOfDays;
    }

    public MealsCalculationService(Meals meals){
        this.numberOfPeople = BigDecimal.valueOf(meals.getNumberOfPeople());
        this.mealsPerDay = BigDecimal.valueOf(meals.getMealsPerDay());
        this.mealEstimatedValue = BigDecimal.valueOf(meals.getMealEstimatedValue());
        this.numberOfDays = BigDecimal.valueOf(meals.getNumberOfDays());
    }

 
    public BigDecimal calculate() {
        return numberOfPeople.multiply(mealsPerDay).multiply(mealEstimatedValue).multiply(numberOfDays).setScale(2, BigDecimal.ROUND_DOWN);
    }


}
