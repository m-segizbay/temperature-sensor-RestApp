package kz.segizbay.FirstRestApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Name should be not empty!")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
