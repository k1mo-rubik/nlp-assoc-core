//package ru.vslukianenko.vsrestintegrservice.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Конфигурационный класс для создания Kafka топиков
// */
//@Configuration
//public class KafkaTopicConfig {
//
//    @Bean
//    public NewTopic createSimpleTopic(){
//        return new NewTopic("simple", 1, (short) 1);
//    }
//}