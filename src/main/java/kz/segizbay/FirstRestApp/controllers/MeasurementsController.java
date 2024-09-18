package kz.segizbay.FirstRestApp.controllers;

import jakarta.validation.Valid;
import kz.segizbay.FirstRestApp.dto.MeasurementDTO;
import kz.segizbay.FirstRestApp.models.Measurement;
import kz.segizbay.FirstRestApp.services.MeasurementsService;
import kz.segizbay.FirstRestApp.services.SensorsService;
import kz.segizbay.FirstRestApp.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kz.segizbay.FirstRestApp.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final SensorsService sensorsService;
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(SensorsService sensorsService, MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.sensorsService = sensorsService;
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult){
        Measurement measurementToAdd = contertToMeasurement(measurementDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        measurementsService.save(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDTO> getAll(){
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).toList();
    }

    @GetMapping("/rainyDaysCount")
    public Integer rainyDaysCount(){
        return measurementsService.findByRaining();
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement m) {
        return modelMapper.map(m, MeasurementDTO.class);
    }

    private Measurement contertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
