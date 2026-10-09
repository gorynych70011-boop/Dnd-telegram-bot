package ru.dndbot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import ru.dndbot.command.*;
import ru.dndbot.core.BotLogic;
import ru.dndbot.telegram.TelegramBot;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import io.github.cdimascio.dotenv.Dotenv;


public class Main {
    public static void main(String[] args) throws Exception {
        Dotenv env = Dotenv.load();

        String token = env.get("BOT_TOKEN");
        String proxyHost = env.get("PROXY_HOST");
        int proxyPort = Integer.parseInt(env.get("PROXY_PORT"));
        String proxyUser = env.get("PROXY_USER");
        String proxyPassword = env.get("PROXY_PASSWORD");

        // SOCKS5
        System.setProperty("socksProxyHost", env.get("PROXY_HOST"));
        System.setProperty("socksProxyPort", env.get("PROXY_PORT"));

        // Аутентификация прокси
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        env.get("PROXY_USER"),
                        env.get("PROXY_PASSWORD").toCharArray()
                );
            };
        });
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
