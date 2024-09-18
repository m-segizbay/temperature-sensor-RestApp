package kz.segizbay.FirstRestApp.exceptions;

import kz.segizbay.FirstRestApp.util.MeasurementErrorResponse;
import kz.segizbay.FirstRestApp.util.MeasurementValidException;
import kz.segizbay.FirstRestApp.util.SensorErrorResponse;
import kz.segizbay.FirstRestApp.util.SensorValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> exceptionHendler(SensorValidException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(MeasurementValidException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
