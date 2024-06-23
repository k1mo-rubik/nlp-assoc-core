package ru.vslukianenko.vsalertservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vslukianenko.vsalertservice.model.AlertMessage;
import ru.vslukianenko.vsalertservice.service.AlertService;

@RestController
@RequestMapping()
@Slf4j
public class MetricsController {


    private final AlertService alertService;

    public MetricsController(AlertService alertService) {
        this.alertService = alertService;
    }

//    @PostMapping
//    public ResponseEntity<String> report(@RequestBody String str) {
//        if (str != null) {
//            alertService.addStrToAlert(str);
//            return ResponseEntity.ok("Оповещение получено");
//        } else {
//            return ResponseEntity.badRequest().body("Некорректные данные");
//        }
//    }

    @PostMapping("/alert")
    public ResponseEntity<String> reportAlert(@RequestBody AlertMessage alertMessage) {
        if (alertMessage != null) {
            log.info("Received message from {} with: {} in time: {}", alertMessage.getAppName(), alertMessage.getMessage(), alertMessage.getTime());
            alertService.addMsgToAlert(alertMessage);
            return ResponseEntity.ok("Оповещение получено");
        } else {
            return ResponseEntity.badRequest().body("Некорректные данные");
        }
    }


}
