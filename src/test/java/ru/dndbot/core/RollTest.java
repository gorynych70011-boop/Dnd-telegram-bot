package ru.dndbot.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dndbot.command.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RollTest {
    @Test
    void rollCommandReturnsSomething() throws IOException {
        RollCommand rc = new RollCommand();

        String result = rc.execute("Бросок d20: **  **", 0);

        assertNotNull(result);
    }

    @Test
    void rollCommandReturnsNumberBetween1And20() throws IOException {
        RollCommand rc = new RollCommand();

        String result = rc.execute("", 0);

        String[] parts = result.split("\\*\\*");

        int rolledNumber = Integer.parseInt(parts[1]);

        assertTrue(rolledNumber >= 1 && rolledNumber <= 20);
    }
}
