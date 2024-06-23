package ru.vslukianenko.vscoreservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumerService {


    String alertSrvUrl = "http://localhost:8082/report";


    private final RestTemplate restTemplate = new RestTemplate();

    @KafkaListener(topics = "simple", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

    @ExceptionHandler(Exception.class)
    private void exceptionHandler(Exception e){
        System.out.println("works");
        restTemplate.postForEntity(alertSrvUrl, e, String.class);

    }
}
