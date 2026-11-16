package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ActionCommand implements Command {
    @Override
    public String name() {
        return "action";
    }

    @Override
    public String shortDescription() {
        return "действие в бою";
    }

    @Override
    public String usage() {
        return "Действие в бою\n" +
                "`/action` - показать действия в бою\n" +
                "Например: `/action heal` - выпить лечебное зелье";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        Battle currentBattle = BattleStorage.getBattle(telegramId);

        if (currentBattle == null) {
            return "Вы не в бою! Действия доступны только во время сражения.";
        }

        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if (args == null || args.isBlank()) {
            StringBuilder menu = new StringBuilder("Доступные действия \n");

            boolean hasHealItem = player.inventory.getItems().stream().anyMatch(item -> item.getBonusHp() > 0);

            if (hasHealItem) {
                menu.append("• `/action heal` - выпить лечебное зелье/съесть еду\n");
            } else {
                menu.append("• У вас нет лечебных предметов в инвентарею\n");
            }

            menu.append("\n`/battle attack` - ударить\n");
            menu.append("`/battle escape` - сбежать");

            return menu.toString();
        }
        if (args.trim().equalsIgnoreCase("heal")) {
            Item healItem = null;
            if (player.inventory != null) {
                for (Item item : player.inventory.getItems()) {
                    if (item.getBonusHp() > 0) {
                        healItem = item;
                        break;
                    }
                }
            }
            if (healItem == null) {
                return "У вас нет лечебных предметов!";
            }
            player.character.heal(healItem.getBonusHp());

            player.inventory.removeItem(healItem.getName());

            Player.save(player, playerDir);

            String enemyTurnLog = currentBattle.enemyTurn();

            StringBuilder result = new StringBuilder();

            result.append("Вы использовали ").append(healItem.getName())
                    .append(" и восстановили ").append(healItem.getBonusHp()).append(" HP!\n");
            result.append("Ваше текущее HP: ").append(player.character.getHp()).append("\n\n");

            result.append("Пока вы пили зелье, враг не спал:\n");
            result.append(enemyTurnLog);

            if (currentBattle.isOver()) {
                BattleStorage.removeBattle(telegramId);
                result.append("\n\nБой окончен.");
            } else {
                result.append("\n\nНапишите `/battle attack`, чтобы ударить, или `/action`, чтобы посмотреть меню.");
            }

            return result.toString();
        }

        return "Неизвестное действие. Напишите `/action`, чтобы посмотреть список.";
    }
}
