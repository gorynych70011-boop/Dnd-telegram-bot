package ru.dndbot.core;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import ru.dndbot.command.CharacterCommand;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;


class CharacterTest {
    @TempDir
    Path tempDir;

    @Test
    void characterCommandReturnsMenuWhenNoArgs() throws IOException {
        CharacterCommand cc = new CharacterCommand();
        String result = cc.execute("", 0);


        assertTrue(result.contains("В меню вы можете"));
        assertTrue(result.contains("0 - создать самому"));
    }

    @Test
    void characterCommandReturnsUsageWhenChoiceZero() throws IOException {
        CharacterCommand cc = new CharacterCommand();
        String result = cc.execute("0", 0);


        assertTrue(result.contains("Создание персонажа"));
        assertTrue(result.contains("Доступные расы"));
    }

    @Test
    void characterCommandReturnsErrorWhenInvalidStats() throws IOException {
        CharacterCommand cc = new CharacterCommand();
        String result = cc.execute("Dima Chirykin Human Fighter Student abc 12 14 8 10 16", 0);

        assertTrue(result.contains("должны быть целыми числами") || result.contains("не является числом"));
    }

    @Test
    void characterCommandReturnsErrorWhenTooFewParams() throws IOException {
        CharacterCommand cc = new CharacterCommand();
        String result = cc.execute("Dima Chirykin", 0);

        assertTrue(result.contains("Ошибка") || result.contains("слишком мало"));
    }
    @Test
    void characterCommandCreatesPresentCharacter() throws IOException{
        CharacterCommand cc = new CharacterCommand();
        String result = cc.execute("1", 0);

        assertTrue(result.contains("Выбран персонаж:"));
    }

    @Test
    void characterCommandSavesAndReads() throws IOException{
        CharacterCommand cc = new CharacterCommand();
        long TestId = 888888L;

        ru.dndbot.core.Character testChar = new ru.dndbot.core.Character("TestName", "TestSurname", "Elf", "Human", "Student",
                15, 14, 13, 12, 11, 10);

        Player.save(new Player(TestId, testChar), tempDir);

        Player savedPlayer = PlayerRead.findByTelegramId(TestId, tempDir);

        assertEquals("TestName", savedPlayer.character.getName());
        assertEquals(15, savedPlayer.character.getStrength());
        assertEquals(14, savedPlayer.character.getDexterity());
        assertEquals(13, savedPlayer.character.getConstitution());
        assertEquals(12, savedPlayer.character.getIntelligence());
        assertEquals(11, savedPlayer.character.getWisdom());
        assertEquals(11, savedPlayer.character.getCharisma());
    }
}
