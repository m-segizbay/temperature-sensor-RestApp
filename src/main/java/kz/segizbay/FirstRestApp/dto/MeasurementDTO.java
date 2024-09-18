package kz.segizbay.FirstRestApp.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {

    @NotNull(message = "Value should be not empty!")
    @Min(value = 0, message = "Value must be greater than or equal to -100")
    @Max(value = 100, message = "Value must be less than or equal to 100")
    private double value;

    @NotNull(message = "Raining should be not empty!")
    private boolean raining;

    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
