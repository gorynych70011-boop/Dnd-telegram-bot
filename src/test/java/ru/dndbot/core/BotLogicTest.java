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
        registry.register(new CharacterCommand());
        registry.register(new RollCommand());
        registry.register(new CheckCommand());
        registry.register(new HelpCommand(registry));

        logic = new BotLogic(registry);
    }

    @Test
    void helpListsAllCommands() {
        String reply = logic.handle("/help", 0);
        assertTrue(reply.contains("/author"));
        assertTrue(reply.contains("/about"));
        assertTrue(reply.contains("/character"));
        assertTrue(reply.contains("/roll"));
        assertTrue(reply.contains("/check"));
        assertTrue(reply.contains("/help"));
    }

    @Test
    void helpWithArgumentShowsUsage() {
        assertEquals(new AboutCommand().usage(), logic.handle("/help about", 0));
    }

    @Test
    void helpWithUnknownArgument() {
        assertTrue(logic.handle("/help qwe", 0).contains("не найдена"));
    }

    @Test
    void unknownCommand() {
        assertTrue(logic.handle("/qwe", 0).startsWith("Неизвестная команда"));
    }

    @Test
    void botNameSuffixIsIgnored() {
        assertEquals(logic.handle("/help", 0), logic.handle("/help@dnd_bot", 0));
    }


}