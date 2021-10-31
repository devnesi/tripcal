package dev.nesi.calculators.Lodging;

import java.math.BigDecimal;

import dev.nesi.calculators.ICalculationService;
import dev.nesi.models.Lodging;

public class LodgingCalculationService implements ICalculationService {
    private BigDecimal numberOfRooms;
    private BigDecimal numberOfDays;
    private BigDecimal averageCost;

    public LodgingCalculationService(
        final BigDecimal numberOfRooms,
        final BigDecimal numberOfDays,
        final BigDecimal averageCost
    ) {
        this.numberOfRooms = numberOfRooms;
        this.numberOfDays = numberOfDays;
        this.averageCost = averageCost;
    }

    public LodgingCalculationService() {

    }

    public LodgingCalculationService(Lodging lodging) {
        this.numberOfRooms = BigDecimal.valueOf(lodging.getNumberOfRooms());
        this.numberOfDays = BigDecimal.valueOf(lodging.getNumberOfDays());
        this.averageCost = BigDecimal.valueOf(lodging.getAverageCost());
    }

    public BigDecimal calculate() {
        return numberOfRooms.multiply(numberOfDays).multiply(averageCost).setScale(2, BigDecimal.ROUND_DOWN);
    }

}
