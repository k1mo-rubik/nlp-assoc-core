package ru.vslukianenko.vsrestintegrservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.vslukianenko.vsrestintegrservice.service.KafkaProducerService;

/**
 * REST-контроллер для обработки Http-запросов
 */
@RestController
@RequestMapping("/api")
public class MessageController {
    // Сервис для отправки сообщений в Kafka
    private final KafkaProducerService kafkaProducerService;

    String alertSrvUrl = "http://localhost:8082/report";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     *  Конструктор для инъекции kafkaProducerService
     * @param kafkaProducerService серви отправки сообщений.
     */
    public MessageController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Метод для обработки POST-запросов и отправки сообщений в kafka.
     * @param message сообщение для отправки
     * @return подтверждение отправки сообщений
     */
    @PostMapping("/msg")
    public ResponseEntity<String> sendMessage(@RequestParam String message) throws Exception {
        kafkaProducerService.sendMessage("simple", message);
        return ResponseEntity.ok("Message sent to kafka");
    }
    @ExceptionHandler(Exception.class)
    private void exceptionHandler(Exception e){
        System.out.println("works");
        restTemplate.postForEntity(alertSrvUrl, e, String.class);

    }
}
