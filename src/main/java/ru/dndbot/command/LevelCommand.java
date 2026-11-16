package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LevelCommand implements Command{
    @Override
    public String name() {
        return "level";
    }

    @Override
    public String shortDescription() {
        return "ваш текущий уровень";
    }

    @Override
    public String usage() {
        return "Просмотр уровня\n" +
                "`/level` - показать ваш текущий уровень и опыт\n";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if (player == null){
            return "Сначала создайте персонажа!";
        }
        return "Статистика игрока: \n\n" +
                "Имя: " + player.character.getName() + "\n" +
                "Уровень: " + player.level + "\n" +
                "Опыт: " + player.xp + " / 100 XP\n\n" +
                "Чтобы получить новый уровень, побеждайте в боях";
    }
}
