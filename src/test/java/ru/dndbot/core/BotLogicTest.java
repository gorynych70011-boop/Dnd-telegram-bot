package ru.dndbot.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dndbot.command.*;

import static org.junit.jupiter.api.Assertions.*;

class BotLogicTest {
    private BotLogic logic;

    @BeforeEach
    void setUp() {
        CommandRegistry registry = new CommandRegistry();
        registry.register(new AuthorCommand());
        registry.register(new AboutCommand());
        registry.register(new HelpCommand(registry));
        logic = new BotLogic(registry);
    }

    @Test
    void helpListsAllCommands() {
        String reply = logic.handle("/help");
        assertTrue(reply.contains("/author"));
        assertTrue(reply.contains("/about"));
        assertTrue(reply.contains("/help"));
    }

    @Test
    void helpWithArgumentShowsUsage() {
        assertEquals(new AboutCommand().usage(), logic.handle("/help about"));
    }

    @Test
    void helpWithUnknownArgument() {
        assertTrue(logic.handle("/help qwe").contains("не найдена"));
    }

    @Test
    void unknownCommand() {
        assertTrue(logic.handle("/qwe").startsWith("Неизвестная команда"));
    }

    @Test
    void botNameSuffixIsIgnored() {
        assertEquals(logic.handle("/help"), logic.handle("/help@dnd_bot"));
    }
}