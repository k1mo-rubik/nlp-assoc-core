package ru.vslukianenko.vsalertservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsService {

    private final List<String> metrics = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.chat.id}")
    private String telegramChatId;

    public synchronized void addMetric(String metric) {
        metrics.add(metric);
    }

    @Scheduled(fixedRate = 60000)
    public synchronized void sendMetricsToAlerts() {
        if (!metrics.isEmpty()) {
            String metricMessage = String.join("\n", metrics);
            sendToDiscord(metricMessage);
            sendToTelegram(metricMessage);
            metrics.clear();
        }
    }

    private void sendToDiscord(String message) {
        String discordMessage = "Ошибки сервисов:\n" + message;
        restTemplate.postForEntity(discordWebhookUrl, discordMessage, String.class);
    }

    private void sendToTelegram(String message) {
        String telegramUrl = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                telegramBotToken, telegramChatId, "Ошибки сервисов:\n" + message);
        restTemplate.getForObject(telegramUrl, String.class);
    }

}
