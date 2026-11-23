package ru.dndbot.core;

public class EnemyFactor {

    public static Enemy createEnemy(String name) {
        Enemy en = null;

        if (name.equalsIgnoreCase("Goblin")) {
            en = new Enemy("Goblin", 30, 8, 4);

            en.addLoot(new Item("Ржавый кинжал", "Обычный", 2, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
            en.addLoot(new Item("Зелье лечения", "Необычный", 0, 0, 20, true));
            en.addLoot(new Item("Кожаная броня", "Обычный", 0, 0, 0, 2, true, EquipmentSlot.ARMOR));
        } else if (name.equalsIgnoreCase("Orc")) {
            en = new Enemy("Orc", 60, 12, 6);

            en.addLoot(new Item("Тяжелый топор", "Необычный", 4, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
            en.addLoot(new Item("Кольчуга", "Редкий", 0, 0, 0, 4, true, EquipmentSlot.ARMOR));
            en.addLoot(new Item("Зелье силы", "Редкий", 3, 0, 0, true));
        } else if (name.equalsIgnoreCase("Dragon")) {
            en = new Enemy("Dragon", 150, 18, 17);

            en.addLoot(new Item("Драконья чешуя", "Эпический", 0, 0, 0, 8, true, EquipmentSlot.ARMOR));
            en.addLoot(new Item("Клык дракона", "Эпический", 6, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
            en.addLoot(new Item("Элексир жизни", "Редкий", 0, 0, 50, true));
        }
        return en;
    }

    public static String getAvailableEnemiesText() {
        return "Доступные противники:\n" +
                "Goblin (Легкий)\n" +
                "Orc (Средний)\n" +
                "Dragon (Сложный)";
    }
}