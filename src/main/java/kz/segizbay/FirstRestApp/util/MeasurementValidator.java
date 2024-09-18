package kz.segizbay.FirstRestApp.util;

import kz.segizbay.FirstRestApp.dto.MeasurementDTO;
import kz.segizbay.FirstRestApp.models.Measurement;
import kz.segizbay.FirstRestApp.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if (!sensorsService.findByName(measurementDTO.getSensor().getName()).isPresent()){
            errors.rejectValue("sensor", "", "This sensor dosnt exist with this name!");
        }
    }
}
