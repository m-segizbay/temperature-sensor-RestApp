package kz.segizbay.FirstRestApp.util;

import kz.segizbay.FirstRestApp.dto.SensorDTO;
import kz.segizbay.FirstRestApp.models.Sensor;
import kz.segizbay.FirstRestApp.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorService;

    @Autowired
    public SensorValidator(SensorsService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.findByName(sensor.getName()).isPresent()){
            errors.rejectValue("name", "", "This name already is taken!");
        }
    }
}
