package ru.vslukianenko.vsalertservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.vslukianenko.vsalertservice.model.AlertMessage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertService {

    private static final String TELEGRAM_API_URL_PATTERN = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
    private final List<AlertMessage> alertsBucket = new CopyOnWriteArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final ServiceParams serviceParams;

    @Scheduled(fixedRate = 30000)
    public synchronized void publishAlert() {
        if (!alertsBucket.isEmpty()) {
            String alertBucketMessages = alertsBucket.stream().map(
                    alertMessage ->
                            alertMessage.getAppName() +
                            ": "+
                            alertMessage.getMessage() +
                            "in time" +
                            alertMessage.getTime()
            ).collect(Collectors.joining("\n"));

            try {
                sendToDiscord(alertBucketMessages);
            } catch (Exception e) {
                log.error("Error in {}: {}, cause", e.getClass(),e.getMessage(), e.getCause());
                throw new RuntimeException(e);
            }
            try {
                sendToTelegram(alertBucketMessages);
            } catch (Exception e) {
                log.error("Error in {}: {}, cause", e.getClass(),e.getMessage(), e.getCause());
                throw new RuntimeException(e);
            }
            alertsBucket.clear();
        }
    }
    public void addMsgToAlert(AlertMessage alertMessage) {
        alertsBucket.add(alertMessage);

    }
    private void sendToDiscord(String message){
        try {
            String discordMessage = "Ошибки сервисов:\n" + message;
            restTemplate.postForEntity(serviceParams.getDiscordWebhookUrl(), discordMessage, String.class);
        } catch (RestClientException e) {
            log.error("Error in {}: {}, cause", e.getClass(),e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
    }

    private void sendToTelegram(String message) {
        try {
            String telegramUrl = String.format(
                    TELEGRAM_API_URL_PATTERN,
                    serviceParams.getTelegramBotToken(),
                    serviceParams.getTelegramChatId(),
                    "Ошибки сервисов:\n" + message
            );
            restTemplate.getForObject(telegramUrl, String.class);
        } catch (RestClientException e) {
            log.error("Error in {}: {}, cause", e.getClass(),e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
    }


}
