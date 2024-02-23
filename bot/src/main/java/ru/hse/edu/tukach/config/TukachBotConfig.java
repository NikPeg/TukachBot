package ru.hse.edu.tukach.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hse.edu.tukach.TukachBot;
import ru.hse.edu.tukach.service.application.ApplicationService;

@Configuration
public class TukachBotConfig {

    @Bean
    public TukachBot tukachBot(ApplicationService applicationService) {
        return new TukachBot(applicationService);
    }

    public TelegramBotsApi telegramBotsApi(TukachBot tukachBot) {
        TelegramBotsApi telegramBotsApi = null;
        try {
			telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(tukachBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
        return telegramBotsApi;
    }
}
