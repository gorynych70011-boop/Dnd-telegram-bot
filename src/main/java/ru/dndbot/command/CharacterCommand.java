package ru.dndbot.command;
import ru.dndbot.core.Character;

public class CharacterCommand implements Command {
    @Override
    public String name() {
        return "character";
    }

    @Override
    public String shortDescription() {
        return "создание персонажа";
    }

    @Override
    public String usage() {
        return "/character — создает персонажа.";
    }

    @Override
    public String execute(String args) {
        Character c1 = new Character("Dima", "Chirykin", "Fighter", "Human",
                "Student", 10, 10, 10, 10, 10, 10,
                10);
        return "Создан персонаж";
    }
}