package ru.dndbot;

import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.dndbot.command.*;
import ru.dndbot.core.BotLogic;
import ru.dndbot.telegram.TelegramBot;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class Main {
    public static void main(String[] args) throws Exception {
        // .env необязателен: если файла нет, берутся обычные переменные окружения
        Dotenv env = Dotenv.configure().ignoreIfMissing().load();

        String token = env.get("BOT_TOKEN");
        if (token == null || token.isBlank()) {
            System.err.println("Не задан BOT_TOKEN (в .env или в переменных окружения)");
            return;
        }

        setupProxy(env);

        CommandRegistry registry = new CommandRegistry();
        registry.register(new AuthorCommand());
        registry.register(new AboutCommand());
        registry.register(new CharacterCommand());
        registry.register(new RollCommand());
        registry.register(new CheckCommand());
        registry.register(new HelpCommand(registry));

        BotLogic logic = new BotLogic(registry);

        try (TelegramBotsLongPollingApplication app = new TelegramBotsLongPollingApplication()) {
            app.registerBot(token, new TelegramBot(token, logic));
            System.out.println("Бот запущен");
            Thread.currentThread().join();
        }
    }

    /** Включает SOCKS5-прокси, только если PROXY_HOST и PROXY_PORT заданы. */
    private static void setupProxy(Dotenv env) {
        String host = env.get("PROXY_HOST");
        String port = env.get("PROXY_PORT");
        if (host == null || host.isBlank() || port == null || port.isBlank()) {
            return;   // без прокси, например при VPN в режиме TUN
        }
        System.setProperty("socksProxyHost", host);
        System.setProperty("socksProxyPort", port);

        String user = env.get("PROXY_USER");
        String password = env.get("PROXY_PASSWORD");
        if (user != null && password != null) {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password.toCharArray());
                }
            });
        }
    }
}
