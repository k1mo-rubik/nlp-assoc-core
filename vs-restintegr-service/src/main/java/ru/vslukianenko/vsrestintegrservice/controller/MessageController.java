package ru.vslukianenko.vsrestintegrservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.vslukianenko.vsrestintegrservice.service.KafkaProducerService;
import ru.vslukianenko.vsrestintegrservice.service.ServiceParams;

/**
 * REST-контроллер для обработки Http-запросов
 */
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    // Сервис для отправки сообщений в Kafka
    private final KafkaProducerService kafkaProducerService;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ServiceParams serviceParams;


    /**
     * Метод для обработки POST-запросов и отправки сообщений в kafka.
     * @param message сообщение для отправки
     * @return подтверждение отправки сообщений
     */
    @PostMapping("/msg")
    public ResponseEntity<String> sendMessage(HttpServletRequest httpServletRequest, @RequestParam String message) throws Exception {
        log.info("Received message: {} from: {}", message, httpServletRequest.getRemoteAddr());
        kafkaProducerService.sendMessage("simple", message);
        return ResponseEntity.ok("Message sent to kafka");
    }
    @ExceptionHandler(Exception.class)
    private void exceptionHandler(Exception e){
        log.error("Handled error {}", e.getMessage());
        restTemplate.postForEntity(serviceParams.getAlertSrvUrl(), e, String.class);
    }
}
