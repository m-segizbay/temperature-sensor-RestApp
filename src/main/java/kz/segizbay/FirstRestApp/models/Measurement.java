package kz.segizbay.FirstRestApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @Min(value = 0, message = "Value must be greater than or equal to -100")
    @Max(value = 100, message = "Value must be less than or equal to 100")
    @NotNull(message = "Value should be not empty!")
    private double value;

    @Column(name = "raining")
    @NotNull(message = "Raining should be not empty!")
    private boolean raining;

    @Column(name = "measurement_date_time")
    private LocalDateTime measurementDateTime;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(double value, boolean raining, LocalDateTime measurementDateTime, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.measurementDateTime = measurementDateTime;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
