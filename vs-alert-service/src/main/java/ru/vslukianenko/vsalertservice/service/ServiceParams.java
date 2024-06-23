package ru.vslukianenko.vsalertservice.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class ServiceParams {
    private String appName = "vs-alert-service";

    @Value("${DS_HOOK_URL}")
    private String discordWebhookUrl;

    @Value("${TG_BOT_TOKEN}")
    private String telegramBotToken;

    @Value("${TG_CHAT_ID}")
    private String telegramChatId;
}
