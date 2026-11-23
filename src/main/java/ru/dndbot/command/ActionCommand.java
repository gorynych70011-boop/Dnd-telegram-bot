package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

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
                "Например: `/action use <Название>` - использовать предмет (зелье. еду)\n\n" +
                "Пример: \n" +
                "`/action use Зелье силы`";
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

            boolean hasUsableItems = false;

            for (Item item : player.inventory.getItems()){
                if(!item.isEquippable()){
                    menu.append("• `/action use ").append(item.getName()).append("\n");
                    hasUsableItems = true;
                }
            }

            if (!hasUsableItems) {
                menu.append("• у вас нет используемых предметов (зелий или еды)\n");
            }

            menu.append("\n`/battle attack` - ударить\n");
            menu.append("`/battle escape` - сбежать");

            return menu.toString();
        }
        if (args.trim().toLowerCase().startsWith("use")) {
            String itemName = args.trim().substring(3).trim();

            if (itemName.isEmpty()) {
                return "Укажите название предмета. \nПример: `action use Зелье силы`";
            }

            Item itemToUse = null;
            for(Item item : player.inventory.getItems()){
                if(item.getName().equalsIgnoreCase(itemName) && !item.isEquippable()){
                    itemToUse = item;
                    break;
                }
            }

            if(itemToUse == null){
                return "У вас нет используемого предмета '" + itemName + "' в инвентаре";
            }

            if(!itemToUse.isUsableInBattle()){
                return "Этот предмет нельзя использовать в бою!";
            }

            StringBuilder result = new StringBuilder();
            result.append("Вы использовали").append(itemToUse.getName()).append("!\n");

            boolean effectApplied = false;

            if (itemToUse.getBonusHp() > 0){
                player.character.heal(itemToUse.getBonusHp());
                result.append(" Востановленно ").append(itemToUse.getBonusHp()).append(" HP. ");
                effectApplied = true;
            }

            if (itemToUse.getBonusStr() > 0){
                player.character.setStrength(itemToUse.getBonusStr());
                result.append(" Сила увеличина  на  ").append(itemToUse.getBonusStr()).append(". ");
                effectApplied = true;
            }

            if (itemToUse.getBonusDex() > 0) {
                player.character.setDexterity(player.character.getDexterity() + itemToUse.getBonusDex());
                result.append(" Ловкость увеличина  на  ").append(itemToUse.getBonusDex()).append(". ");
                effectApplied = true;
            }

            if(!effectApplied){return "Этот предмет нельзя использовать (у него нет эффектов)"; }

            result.append("\n Ваше текущее здоровье: ").append(player.character.getHp()).append(" HP:\n\n");

            result.append("\n\n");

            player.inventory.removeItem(itemToUse.getName());

            Player.save(player, playerDir);

            String enemyTurnLog = currentBattle.enemyTurn();

            result.append("Пока вы использовали предмет, враг не спал:\n");
            result.append(enemyTurnLog);
            result.append("\n Ваше здоровье после ответа врага: ").append(player.character.getHp()).append(" HP\n");

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
