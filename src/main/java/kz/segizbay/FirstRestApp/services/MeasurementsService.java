package kz.segizbay.FirstRestApp.services;

import kz.segizbay.FirstRestApp.models.Measurement;
import kz.segizbay.FirstRestApp.models.Sensor;
import kz.segizbay.FirstRestApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement){
        measurementsRepository.save(enrichMeasurement(measurement));
    }

    public Integer findByRaining(){
        return measurementsRepository.countByRainingIsTrue();
    }

    private Measurement enrichMeasurement(Measurement measurement){
        Sensor sensor = sensorsService.findByName(measurement.getSensor().getName()).get();
        measurement.setSensor(sensor);
        measurement.setMeasurementDateTime(LocalDateTime.now());
        return measurement;
    }
}
