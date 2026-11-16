package ru.dndbot.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {
    @Test
    void characterWinsBattle(){
        Character TestChar = new Character("Bot", "Test", "BotTest", "Bot", "Fighter", 10, 10,
                10, 10, 10, 10, 100, 100);
        Enemy TestEnemy = new Enemy("Test", 10, 10, 10);

        Inventory TestInventory = new Inventory();

        Battle battle = new Battle(TestChar, TestInventory, TestEnemy);

        int rounds = 0;
        while (!battle.isOver() && rounds < 100) {
            battle.doRound();
            rounds++;
        }

        assertTrue(battle.isOver());
        assertTrue(TestChar.isAlive());
        assertFalse(TestEnemy.isAlive());
    }
}
