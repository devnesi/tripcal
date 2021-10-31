package dev.nesi.calculators.Gasoline;

import java.math.BigDecimal;
import java.math.RoundingMode;

import dev.nesi.calculators.ICalculationService;
import dev.nesi.models.Gasoline;


public class GasolineCalculationService implements ICalculationService {
    private BigDecimal numberOfKm;
    private BigDecimal kmPerLiter;
    private BigDecimal literCost;
    private BigDecimal numberOfVehicles;


    public GasolineCalculationService(
        final BigDecimal numberOfKm,
        final BigDecimal kmPerLiter,
        final BigDecimal literCost,
        final BigDecimal numberOfVehicles
    ) {
        this.numberOfKm = numberOfKm;
        this.kmPerLiter = kmPerLiter;
        this.literCost = literCost;
        this.numberOfVehicles = numberOfVehicles;
    }

    public GasolineCalculationService(final Gasoline gasoline) {
        this.numberOfKm = BigDecimal.valueOf(gasoline.getNumberOfKm());
        this.kmPerLiter = BigDecimal.valueOf(gasoline.getKmPerLiter());
        this.literCost = BigDecimal.valueOf(gasoline.getLiterCost());
        this.numberOfVehicles = BigDecimal.valueOf(gasoline.getNumberOfVehicles());
    }

    public GasolineCalculationService() {
    }

    public BigDecimal calculate() {
        return numberOfKm.divide(kmPerLiter, RoundingMode.HALF_UP).multiply(literCost).multiply(numberOfVehicles).setScale(2, BigDecimal.ROUND_DOWN);
    }


}
