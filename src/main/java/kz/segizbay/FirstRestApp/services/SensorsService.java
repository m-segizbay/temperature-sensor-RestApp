package kz.segizbay.FirstRestApp.services;

import kz.segizbay.FirstRestApp.models.Sensor;
import kz.segizbay.FirstRestApp.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Sensor sensor){
        enrichSensor(sensor);
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }

    private void enrichSensor(Sensor sensor){
        sensor.setCreatedAt(LocalDateTime.now());
        sensor.setCreatedWho("ADMIN");
    }
}
