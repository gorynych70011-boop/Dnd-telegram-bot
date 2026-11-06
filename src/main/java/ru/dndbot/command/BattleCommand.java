package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                "`/battle` — продолжить бой (сделать удар)\n" +
                "`/battle escape` — сбежать из боя\n\n" +
                "Пример:\n" +
                "`/battle Goblin`";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        Battle currentBattle = BattleStorage.getBattle(telegramId);

        if (currentBattle != null) {
            if (args != null && (args.trim().equalsIgnoreCase("escape"))) {
                BattleStorage.removeBattle(telegramId);
                return "Вы успешно сбежали из боя!";
            }

            String roundLog = currentBattle.doRound();

            if (currentBattle.isOver()) {
                BattleStorage.removeBattle(telegramId);
                return roundLog + "\n\nБой окончен. Вы можете начать новый: /battle <Имя>";
            } else {
                return roundLog + "\n\nНапишите /battle, чтобы ударить снова, или /battle escape, чтобы сбежать.";
            }
        }


        if (args == null || args.isBlank()) {
            return usage();
        }

        if (args.trim().equalsIgnoreCase("escape")) {
            return "Вы сейчас не в бою. Чтобы начать бой, напишите /battle <Имя противника>";
        }

        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if(player == null){ return "Сначала создайте персонажа!"; }

        if(!player.character.isAlive()) {return "Ваш персонаж мертв. Сначала восстановите здоровье!"; }

        Enemy en = new Enemy("Goblin", 50, 15, 10);

        Battle battle = new Battle(player.character, en);
        BattleStorage.saveBattle(telegramId, battle);

        String firstRoundLog = battle.doRound();

        if (battle.isOver()) {
            BattleStorage.removeBattle(telegramId);
            return "Бой начался!\n" + firstRoundLog + "\n\nБой окончен.";
        } else {
            return "Бой начался!\n" + firstRoundLog + "\n\nНапишите /battle, чтобы ударить снова, или /battle escape, чтобы сбежать.";
        }
    }
}

