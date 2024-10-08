package kz.segizbay.FirstRestApp.repositories;

import kz.segizbay.FirstRestApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    Integer countByRainingIsTrue();
}
