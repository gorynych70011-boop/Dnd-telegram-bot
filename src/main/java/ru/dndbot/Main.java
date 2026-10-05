package ru.dndbot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.dndbot.command.*;
import ru.dndbot.core.BotLogic;
import ru.dndbot.telegram.TelegramBot;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class Main {
    public static void main(String[] args) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "user439044",
                        "rw5ajg".toCharArray()
                );
            }
        });
        String token = System.getenv("BOT_TOKEN");
        if (token == null || token.isBlank()){
            System.err.println("Не задана переменная окружения BOT_TOKEN");
            return;
        }
        CommandRegistry registry = new CommandRegistry();
        registry.register(new AuthorCommand());
        registry.register(new AboutCommand());
        registry.register(new HelpCommand(registry));

        BotLogic logic = new BotLogic(registry);

        try (TelegramBotsLongPollingApplication app = new TelegramBotsLongPollingApplication()){
            app.registerBot(token, new TelegramBot(token, logic));
            System.out.println("Бот запущен");
            Thread.currentThread().join();
        }
    }
}
