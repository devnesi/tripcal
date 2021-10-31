package dev.nesi.calculators.Airfare;

import java.math.BigDecimal;
import dev.nesi.calculators.ICalculationService;
import dev.nesi.models.Airfare;

public class AirfareCalculationService implements ICalculationService {
    private BigDecimal costPerPerson;
    private BigDecimal numberOfPeople;
    private BigDecimal vehicleRent;

    public AirfareCalculationService() {

    }
  
    public AirfareCalculationService(
        final BigDecimal costPerPerson,
        final BigDecimal numberOfPeople,
        final BigDecimal vehicleRent
    ) {
        this.costPerPerson = costPerPerson;
        this.numberOfPeople = numberOfPeople;
        this.vehicleRent = vehicleRent;    
    }

    public AirfareCalculationService(final Airfare airfare) {
        this.costPerPerson = BigDecimal.valueOf(airfare.getCostPerPerson());
        this.numberOfPeople = BigDecimal.valueOf(airfare.getNumberOfPeople());
        this.vehicleRent = BigDecimal.valueOf(airfare.getVehicleRent());
    }

    public BigDecimal calculate() {
        return costPerPerson.multiply(numberOfPeople).add(vehicleRent).setScale(2, BigDecimal.ROUND_UP);
    }
}
