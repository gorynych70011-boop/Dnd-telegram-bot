package ru.dndbot.core;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Fight {

    public static String startNewBattle(String enemyName, long telegramId) throws IOException {
        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if(player != null){
            player.character.setInventory(player.inventory);
        }

        if(player == null){ return "Сначала создайте персонажа!"; }
        if(!player.character.isAlive()) {return "Ваш персонаж мертв. Сначала восстановите здоровье!"; }

        Enemy en = new Enemy(enemyName, 50, 10, 4);

        if (enemyName.equalsIgnoreCase("Goblin")){
            en.addLoot(new Item("Ржавый кинжал", "Обычный", 2, 0, 0));
            en.addLoot(new Item("Кусок сыра", "Обычный", 0 , 0, 5));
            en.addLoot(new Item("Золотая монета", "Необычный", 0 ,0, 0));
        } else if (enemyName.equalsIgnoreCase("Orc")) {
            en.addLoot(new Item("Тяжелый топор", "Необычный", 4, 0, 0));
            en.addLoot(new Item("Клык орка", "Редкий", 0, 2, 0));
            en.addLoot(new Item("Зелье силы", "Редкий",7, 0, 0));
        } else {
            en.addLoot(new Item("Странный камень", "Мусор",1, 0,0));
        }

        Battle battle = new Battle(player.character, player.inventory, en);
        BattleStorage.saveBattle(telegramId, battle);

        Character c = player.character;
        StringBuilder intro = new StringBuilder();

        intro.append("Начинается бой!\n\n");

        intro.append("Ваш персонаж: ").append(c.getName()).append(" ").append(c.getSurname()).append(" \n");
        intro.append("HP: ").append(c.getHp()).append(" ").append("| AC: ").append(c.getArmor()).append(" ");
        intro.append("| STR: ").append(c.getStrength()).append(" ").append("| DEX: ").append(c.getDexterity()).append(" ").append("| INT: ")
                .append(c.getIntelligence()).append("\n\n");
        intro.append("| CHA: ").append(c.getCharisma()).append(" ").append("| CON: ").append(c.getConstitution()).append(" ").append("| WIS: ")
                .append(c.getWisdom()).append("\n\n");

        intro.append("Противник: ").append(en.getName()).append(" \n");
        intro.append("HP: ").append(en.getHp()).append(" ").append("| AC: ").append(en.getArmor()).append(" ");
        intro.append("| STR: ").append(en.getStrength()).append("\n\n");

        String firstRoundLog = battle.doRound();
        intro.append(firstRoundLog);

        if (battle.isOver()) {
            BattleStorage.removeBattle(telegramId);
            String lootMsg = giveLoot(player, en, playerDir);
            return intro.toString() + lootMsg + "\n\nБой окончен.";
        } else {
            return intro.toString() + "\n\nНапишите /battle attack, чтобы ударить снова, или /battle escape, чтобы сбежать.";
        }
    }


    public static String nextRound(long telegramId) throws IOException {
        Battle battle = BattleStorage.getBattle(telegramId);
        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if (battle == null || player == null) {
            return "Ошибка: бой или персонаж не найдены.";
        }

        String roundLog = battle.doRound();
        Enemy en = battle.getEnemy();

        if (battle.isOver()) {
            BattleStorage.removeBattle(telegramId);
            String lootMsg = giveLoot(player, en, playerDir);
            return roundLog + lootMsg + "\n\nБой окончен.";
        } else {
            return roundLog + "\n\nНапишите /battle attack, чтобы ударить снова, или /battle escape, чтобы сбежать.";
        }
    }

    private static String giveLoot(Player player, Enemy en, Path playerDir) throws IOException {
        if (player.character.isAlive() && !en.isAlive()) {
            Item loot = en.rollLoot();
            if (loot != null) {
                player.inventory.addItem(loot);
                Player.save(player, playerDir);
                return "\n\n Добыча:  Вы получили " + loot.toString();
            } else {
                return "\n\nС этого противника не выпало добычи.";
            }
        }
        return "";
    }
}