package ru.dndbot.core;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayerRead {
    private static final Gson GSON = new Gson();

    public static Player findByTelegramId(long telegramId) throws IOException{
        return findByTelegramId(telegramId, Path.of("players"));
    }

    public static Player findByTelegramId(long telegramId, Path dir) throws IOException {
        Path file = dir.resolve(telegramId + ".json");

        if (!Files.exists(file)) {
            System.out.println("Файл не найден: " + file.toAbsolutePath()); // Для отладки
            return null; // Игрок не найден
        }

        return GSON.fromJson(Files.readString(file), Player.class);
    }
}
