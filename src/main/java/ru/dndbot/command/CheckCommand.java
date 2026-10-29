package ru.dndbot.command;

import ru.dndbot.core.Character;
import ru.dndbot.core.Check;
import ru.dndbot.core.Player;
import ru.dndbot.core.PlayerRead;

import java.io.IOException;
import java.util.Locale;
import java.nio.file.Path;

public class CheckCommand implements Command {
    @Override
    public String name() {
        return "check";
    }

    @Override
    public String shortDescription() {
        return "проверка характеристики против сложности";
    }

    @Override
    public String usage() {
        return "Проверка характеристики против сложности\n" +
                "`/check <характеристика> <сложность>`\n\n" +
                "Доступные характеристики: STR, DEX, CON, INT, WIS, CHA\n" +
                "Сложность — целое число.\n\n" +
                "Модификатор = значение характеристики / 2 (округление вниз).\n" +
                "Успех, если d20 + модификатор ≥ сложность.\n\n" +
                "Пример:\n" +
                "`/check STR 15`";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        if (args == null || args.isBlank()) {
            return usage();
        }

        String[] parts = args.trim().split("\\s+");
        if (parts.length < 2) {
            return "Ошибка: нужно указать характеристику и сложность.\n" + usage();
        }

        String statName = parts[0].toUpperCase(Locale.ROOT);
        int difficulty;
        try {
            difficulty = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return "Ошибка: сложность должна быть целым числом.\n" + usage();
        }

        Player player = PlayerRead.findByTelegramId(telegramId, Path.of("players"));
        if (player == null || player.character == null) {
            return "У вас ещё нет персонажа. Создайте его командой /character";
        }

        Character c = player.character;
        int statValue;
        switch (statName) {
            case "STR" -> statValue = c.getStrength();
            case "DEX" -> statValue = c.getDexterity();
            case "CON" -> statValue = c.getConstitution();
            case "INT" -> statValue = c.getIntelligence();
            case "WIS" -> statValue = c.getWisdom();
            case "CHA" -> statValue = c.getCharisma();
            default -> {
                return "Ошибка: неизвестная характеристика '" + parts[0] + "'.\n" + usage();
            }
        }

        // Используем Check.checkStat — он сам бросает d20 и считает модификатор
        boolean success = Check.checkStat(statValue, difficulty);

        // Отдельно бросаем ещё раз только для отображения расшифровки
        int dice = Check.roll();
        int modifier = Math.floorDiv(statValue, 2);
        int total = dice + modifier;
        String sign = modifier >= 0 ? "+" : "";

        String result = success ? " Успех!" : " Провал";

        return result + "\n" +
                "Проверка " + statName + " (значение " + statValue + ", модификатор " + sign + modifier + ")\n" +
                " d20: **" + dice + "** " + sign + modifier + " = **" + total + "**\n" +
                "Сложность: **" + difficulty + "**";
    }
}
