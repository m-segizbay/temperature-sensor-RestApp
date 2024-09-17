package kz.segizbay.FirstRestApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sensors")
public class SensorController {
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor()
}
