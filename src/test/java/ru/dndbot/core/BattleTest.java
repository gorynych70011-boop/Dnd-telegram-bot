package ru.dndbot.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {
    @Test
    void characterWinsBattle(){
        Character TestChar = new Character("Bot", "Test", "BotTest", "Bot", "Fighter", 10, 10,
                10, 10, 10, 10, 100, 100);
        Enemy TestEnemy = new Enemy(50);

        Battle battle = new Battle(TestChar, TestEnemy);

        boolean result = battle.fight();

        assertTrue(result);
        assertTrue(TestChar.isAlive());
        assertFalse(TestEnemy.isAlive());
    }
}
