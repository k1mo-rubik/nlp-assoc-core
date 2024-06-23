package ru.vslukianenko.vsrestintegrservice.service;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * Сервис для отправки сообщений в Kafka
 */
@Service
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
    public void sendMessage(String topic, String message){
        kafkaTemplate.send(topic, message);
    }

}
