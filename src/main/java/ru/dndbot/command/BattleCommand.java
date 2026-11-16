package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;

public class BattleCommand implements Command{
    @Override
    public String name() {
        return "battle";
    }

    @Override
    public String shortDescription() {
        return "пошаговая битва";
    }

    @Override
    public String usage() {
        return "Управление битвой\n" +
                "`/battle <Имя противника>` — начать новый бой\n" +
                "`/battle attack` — продолжить бой (сделать удар)\n" +
                "`/battle escape` — сбежать из боя\n\n" +
                "Пример:\n" +
                "`/battle Goblin`";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        Battle currentBattle = BattleStorage.getBattle(telegramId);

        if (currentBattle != null) {
            if (args != null && (args.trim().equalsIgnoreCase("escape") || args.trim().equalsIgnoreCase("сбежать"))) {
                BattleStorage.removeBattle(telegramId);
                return "Вы успешно сбежали из боя!";
            }

            if (args == null || args.isBlank() || (!args.trim().equalsIgnoreCase("attack") && !args.trim().equalsIgnoreCase("атака"))) {
                return "Вы в бою! Напишите `/battle attack`, чтобы ударить, или `/battle escape`, чтобы сбежать.";
            }

            return Fight.nextRound(telegramId);
        }

        if (args == null || args.isBlank()) {
            return usage();
        }

        if (args.trim().equalsIgnoreCase("escape") || args.trim().equalsIgnoreCase("attack")) {
            return "Вы сейчас не в бою. Чтобы начать бой, напишите /battle <Имя противника>";
        }

        String enemyName = args.trim();
        return Fight.startNewBattle(enemyName, telegramId);
    }
}