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
            en.addLoot(new Item("Ржавый кинжал", "Обычный", 2, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
            en.addLoot(new Item("Зелье лечения", "Необычный", 0, 0, 10, true));
            en.addLoot(new Item("Кожаная броня", "Обычный", 0 ,0, 0, 2, true, EquipmentSlot.ARMOR));
        } else if (enemyName.equalsIgnoreCase("Orc")) {
            en.addLoot(new Item("Тяжелый топор", "Необычный", 4, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
            en.addLoot(new Item("Кольчуга", "Редкий", 0, 0, 0, 4, true, EquipmentSlot.ARMOR));
            en.addLoot(new Item("Зелье силы", "Редкий",7, 0, 0, true));
        } else {
            en.addLoot(new Item("Странный камень", "Мусор",1, 0,0, false));
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
            return roundLog + "\n\nНапишите /battle attack, чтобы ударить снова, или /battle escape, чтобы сбежать." +
                    " Напишите /action чтобы воспользоваться предметами.";
        }
    }

    private static String giveLoot(Player player, Enemy en, Path playerDir) throws IOException {
        if (player.character.isAlive() && !en.isAlive()) {
            StringBuilder result = new StringBuilder();

            Item loot = en.rollLoot();
            if (loot != null) {
                player.inventory.addItem(loot);
                result.append("\n\nДобыча: Вы получили ").append(loot.toString());
            } else {
                result.append("\n\nС этого противника не выпало добычи.");
            }

            int xpReward = 50;
            player.xp += xpReward;
            result.append("\nВы получили ").append(xpReward).append(" опыта! (Всего: ").append(player.xp).append("/100)");

            while (player.xp >= 100) {
                player.level++;
                player.xp -= 100;
                result.append("\n\nПОВЫШЕНИЕ УРОВНЯ! Теперь ваш уровень: ").append(player.level).append("!");
            }

            Player.save(player, playerDir);

            return result.toString();
        }
        return "";
    }
}