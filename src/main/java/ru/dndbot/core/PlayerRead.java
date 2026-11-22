package ru.dndbot.core;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayerRead {

    private static final Gson GSON = new Gson();

    // Читаем из папки players/
    public static Player findByTelegramId(long telegramId, Path dir) throws IOException {
        Path file = dir.resolve(telegramId + ".json");

        if (!Files.exists(file)) {
            System.out.println("Файл не найден: " + file.toAbsolutePath()); // Для отладки
            return null; // Игрок не найден
        }

        Player player = GSON.fromJson(Files.readString(file), Player.class);

        if (player != null){
            if(player.inventory == null) player.inventory = new Inventory();
            if(player.level == 0) player.level =1;

            if (player.character != null && player.character.getEquipment() == null){
                player.character.setEquipment(new Equipment());
            }
        }
        return player;
    }
}
