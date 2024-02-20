package ru.hse.edu.tukach;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hse.edu.tukach.config.BotConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hse.edu.tukach.service.application.ApplicationService;

@SpringBootApplication
public class TukachApplication {
	String token;
	public static void main(String[] args) {
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			BotConfig botConfig = new BotConfig();

			telegramBotsApi.registerBot(new TukachBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
