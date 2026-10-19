package ru.dndbot.core;
import com.google.gson.Gson;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;


public class Player {
    public long telegramId;
    public Character character;

    private static final Gson GSON = new Gson();
    private static final Path DIR = Path.of("players");

    public Player(long telegramId, Character character){
        this.telegramId = telegramId;
        this.character = character;
    }

    public static void save(Player player) throws IOException {
        Files.createDirectories(DIR);
        Path file = DIR.resolve(player.telegramId + ".json");
        Files.writeString(file, GSON.toJson(player));

    }

    public static Player findByTelegramId(long telegramId) throws IOException {
        Path file = DIR.resolve(telegramId + ".json");
        return GSON.fromJson(Files.readString(file), Player.class);
    }
}
