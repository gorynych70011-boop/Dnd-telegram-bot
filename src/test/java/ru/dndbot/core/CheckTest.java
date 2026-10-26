package ru.dndbot.core;

import org.junit.jupiter.api.Test;
import ru.dndbot.command.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class CheckTest {
    @Test
    void checkCommandReturnsHelp() throws IOException {
        CheckCommand cc = new CheckCommand();
        String result = cc.execute("", 0);

        assertTrue(result.contains("Проверка характеристики"));
    }

    @Test
    void checkCommandReturnsInvalidStat() throws IOException {
        CheckCommand cc = new CheckCommand();
        String result = cc.execute("FOO 15", 0);

        assertTrue(result.contains("неизвестная характеристика") || result.contains("нет персонажа"));
    }

    @Test
    void checkCommandReturnsInvalidDifficulty() throws IOException {
        CheckCommand cc = new CheckCommand();
        String result = cc.execute("STR abc", 0);

        assertTrue(result.contains("должна быть целым числом"));
    }
}

