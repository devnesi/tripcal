package dev.nesi.models;

import android.annotation.SuppressLint;

import com.google.firebase.database.Exclude;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import dev.nesi.TabAero;
import dev.nesi.TabGasolina;
import dev.nesi.TabHospedagem;
import dev.nesi.TabRefeicoes;
import dev.nesi.calculators.Airfare.AirfareCalculationService;
import dev.nesi.calculators.Gasoline.GasolineCalculationService;
import dev.nesi.calculators.Lodging.LodgingCalculationService;
import dev.nesi.calculators.Meals.MealsCalculationService;

public class Viagem {
    private String name, destiny;
    private Long dtInit, dtFinish;
    private Integer travelers;

    private Gasoline gasoline;
    private Airfare airfare;
    private Lodging lodging;
    private Meals meals;

    private BigDecimal total;

    public Viagem() {
        this.travelers = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public Long getDtInit() {
        return dtInit;
    }

    public void setDtInit(Long dtInit) {
        this.dtInit = dtInit;
    }

    public Long getDtFinish() {
        return dtFinish;
    }

    public void setDtFinish(Long dtFinish) {
        this.dtFinish = dtFinish;
    }

    public Integer getTravelers() {
        return travelers;
    }

    public void setTravelers(Integer travelers) {
        this.travelers = travelers;
    }

    @Exclude
    public BigDecimal getTotal() {
        return total;
    }

    @Exclude
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Gasoline getGasoline() {
        return gasoline;
    }

    public void setGasoline(Gasoline gasoline) {
        this.gasoline = gasoline;
    }

    public Airfare getAirfare() {
        return airfare;
    }

    public void setAirfare(Airfare airfare) {
        this.airfare = airfare;
    }

    public Lodging getLodging() {
        return lodging;
    }

    public void setLodging(Lodging lodging) {
        this.lodging = lodging;
    }

    public Meals getMeals() {
        return meals;
    }

    public void setMeals(Meals meals) {
        this.meals = meals;
    }

    public Integer getDiferenceDates(){
        if(getDtInit() == null || getDtFinish() == null){
            return 1;
        }

        return getDateDiff(getDtInit(), getDtFinish());
    }

    private Integer getDateDiff(long date1, long date2) {
        long diffInMillies = date2 - date1;
        return (int) TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    @SuppressLint("NewApi")
    public BigDecimal calculaTotal(){
        BigDecimal total = new BigDecimal(0);

        if(getGasoline() != null){
            total = total.add(new GasolineCalculationService(gasoline).calculate());
        }

        if(getAirfare() != null){
            airfare.setNumberOfPeople(travelers);
            total = total.add(new AirfareCalculationService(airfare).calculate());
        }

        if(getLodging() != null){
            total = total.add(new LodgingCalculationService(lodging).calculate());
        }

        if(getMeals() != null){
            meals.setNumberOfPeople(travelers);
            meals.setNumberOfDays(getDiferenceDates());
            total = total.add(new MealsCalculationService(meals).calculate());
        }

        return total;
    }
}
