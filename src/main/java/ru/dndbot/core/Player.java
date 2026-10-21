package ru.dndbot.core;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Player {
    public long telegramId;
    public Character character;

    private static final Gson GSON = new Gson();

    public Player(long telegramId, Character character){
        this.telegramId = telegramId;
        this.character = character;
    }

    // Сохраняем в папку players/
    public static void save(Player player) throws IOException {
        Path dir = Path.of("players");
        if (!Files.exists(dir)) {
            Files.createDirectories(dir); // Создаем папку, если её нет
        }

        Path file = dir.resolve(player.telegramId + ".json");
        Files.writeString(file, GSON.toJson(player));
        System.out.println("Персонаж сохранен в: " + file.toAbsolutePath()); // Для отладки
    }

    // Читаем из папки players/
    public static Player findByTelegramId(long telegramId) throws IOException {
        Path file = Path.of("players", telegramId + ".json");

        if (!Files.exists(file)) {
            System.out.println("Файл не найден: " + file.toAbsolutePath()); // Для отладки
            return null; // Игрок не найден
        }

        return GSON.fromJson(Files.readString(file), Player.class);
    }
}