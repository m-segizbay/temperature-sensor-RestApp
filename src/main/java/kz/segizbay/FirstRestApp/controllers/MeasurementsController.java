package kz.segizbay.FirstRestApp.controllers;

import jakarta.validation.Valid;
import kz.segizbay.FirstRestApp.dto.MeasurementDTO;
import kz.segizbay.FirstRestApp.dto.SensorDTO;
import kz.segizbay.FirstRestApp.models.Measurement;
import kz.segizbay.FirstRestApp.models.Sensor;
import kz.segizbay.FirstRestApp.services.MeasurementsService;
import kz.segizbay.FirstRestApp.services.SensorsService;
import kz.segizbay.FirstRestApp.util.MeasurementErrorResponse;
import kz.segizbay.FirstRestApp.util.MeasurementValidException;
import kz.segizbay.FirstRestApp.util.MeasurementValidator;
import kz.segizbay.FirstRestApp.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors){
                errorsMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementValidException(errorsMsg.toString());
        }
        measurementsService.save(contertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(MeasurementValidException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
        MeasurementDTO measurementDTO = modelMapper.map(m, MeasurementDTO.class);
        SensorDTO sensorDTO = modelMapper.map(m.getSensor(), SensorDTO.class);
        measurementDTO.setSensor(sensorDTO);
        return measurementDTO;
    }

    private Measurement contertToMeasurement(MeasurementDTO m){
        Measurement measurement = modelMapper.map(m, Measurement.class);
        Sensor sensor = sensorsService.findByName(m.getSensor().getName()).get();
        measurement.setSensor(sensor);
        return measurement;
    }
}
