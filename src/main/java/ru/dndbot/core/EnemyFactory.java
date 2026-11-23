package ru.dndbot.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class EnemyFactory {

    private static final Map<String, Supplier<Enemy>> ENEMY_TEMPLATES = new HashMap<>();

    static {
        ENEMY_TEMPLATES.put("goblin", () -> {
                    Enemy e = new Enemy("Goblin", 30, 8, 4);

                    e.addLoot(new Item("Ржавый кинжал", "Обычный", 2, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
                    e.addLoot(new Item("Зелье лечения", "Необычный", 0, 0, 20, true));
                    e.addLoot(new Item("Кожаная броня", "Обычный", 0, 0, 0, 2, true, EquipmentSlot.ARMOR));
                    return e;
                });

        ENEMY_TEMPLATES.put("orc", () -> {
                    Enemy e = new Enemy("Orc", 60, 12, 6);

                    e.addLoot(new Item("Тяжелый топор", "Необычный", 4, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
                    e.addLoot(new Item("Кольчуга", "Редкий", 0, 0, 0, 4, true, EquipmentSlot.ARMOR));
                    e.addLoot(new Item("Зелье силы", "Редкий", 3, 0, 0, true));
                    return e;
                });

        ENEMY_TEMPLATES.put("dragon", () -> {
                Enemy e = new Enemy("Dragon", 150, 18, 17);

                e.addLoot(new Item("Драконья чешуя", "Эпический", 0, 0, 0, 8, true, EquipmentSlot.ARMOR));
                e.addLoot(new Item("Клык дракона", "Эпический", 6, 0, 0, 0, true, EquipmentSlot.MAIN_HAND));
                e.addLoot(new Item("Элексир жизни", "Редкий", 0, 0, 50, true));
                return e;
        });

        }

    public static Enemy createEnemy(String name){
        if(name == null)return null;
        Supplier<Enemy> factory = ENEMY_TEMPLATES.get(name.toLowerCase());
        return factory != null ? factory.get() : null;
    }

    public static String getAvailableEnemiesText() {
        return "Доступные противники:\n" +
                "Goblin (Легкий)\n" +
                "Orc (Средний)\n" +
                "Dragon (Сложный)";
    }
}