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


    public Player(long telegramId, Character character){
        this.telegramId = telegramId;
        this.character = character;
    }

    public static void save(Player player) throws IOException {
        String FILE = player.telegramId + ".json";
        Files.writeString(Path.of(FILE),GSON.toJson(player));
    }

    public static Player findByTelegramId(long telegramId) throws IOException {
        String FILE = telegramId + ".json";
        return GSON.fromJson(

    Files.readString(Path.of(FILE)), Player.class);
    }

}
