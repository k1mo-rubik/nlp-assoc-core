package ru.vslukianenko.vsalertservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vslukianenko.vsalertservice.service.MetricsService;

@RestController
@RequestMapping("/report")
public class MetricsController {


    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @PostMapping
    public ResponseEntity<String> reportMetric(@RequestBody String str) {
        if (str != null) {
            metricsService.addMetric(str);
            return ResponseEntity.ok("Оповещение получено");
        } else {
            return ResponseEntity.badRequest().body("Некорректные данные");
        }
    }


}
