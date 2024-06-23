package ru.vslukianenko.vsrestintegrservice.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * Сервис для отправки сообщений в Kafka
 */
@Service
@Slf4j
public class KafkaProducerService {
    // Шаблон для взаимоджействия Kafka
    private final KafkaTemplate<String,String> kafkaTemplate;

    /**
     *  Конструктор для инъекции KafkaTemplate
     * @param kafkaTemplate шаблон для отправки сообщений
     */
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Метод для отправки сообщений в указанный топик
     * @param topic топик, в который будет отправелно соодщение
     * @param message сообщение
     */
    public void sendMessage(String topic, String message) throws Exception {
        try {
            kafkaTemplate.send(topic, message);
            log.info("Message: {} sent to topic {}", message, topic);
        } catch (Exception e){
            log.error("Error in {}: {}, cause", e.getClass(),e.getMessage(), e.getCause());
            throw new Exception("Restintegr-service exception",e);
        }
    }

}
