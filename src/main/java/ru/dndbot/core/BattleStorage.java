package ru.dndbot.core;

import java.util.HashMap;
import java.util.Map;

public class BattleStorage {
    private static final Map<Long, Battle> activeBattles = new HashMap<>();

    public static void saveBattle(long telegramId, Battle battle) {
        activeBattles.put(telegramId, battle);
    }

    public static Battle getBattle(long telegramId){
        return activeBattles.get(telegramId);
    }

    public static void removeBattle(long telegramId){
        activeBattles.remove(telegramId);
    }
}
