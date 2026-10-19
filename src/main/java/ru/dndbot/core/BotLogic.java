package ru.dndbot.core;

import ru.dndbot.command.CommandRegistry;

import java.io.IOException;

public class BotLogic {
    private final CommandRegistry registry;

    public BotLogic(final CommandRegistry registry) {
        this.registry = registry;
    }

    public String handle(String text, long telegramId){
        String trimmed = text.trim();
        if (!trimmed.startsWith("/")) {
            return "Я понимаю только команды. Наберите /help";
        }
        String[] parts = trimmed.substring(1).split("\\s+",2);
        String name = parts[0];

        int at = name.indexOf('@');
        if (at >=0){
            name = name.substring(0,at);
        }
        if (name.equals("start")) {
            name="help";
        }
        String args = parts.length > 1 ? parts[1] : "";
        return registry.find(name).map(c -> {
            try {
                return c.execute(args, telegramId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse("Неизвестная команда /" + name + ". Наберите /help");
    }
}
