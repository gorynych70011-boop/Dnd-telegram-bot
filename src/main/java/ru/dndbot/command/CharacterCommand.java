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
        return "Создание персонажа\n" +
                "`/character <Имя> <Фамилия> <Раса> <Класс> <Роль> <STR> <DEX> <CON> <INT> <WIS> <CHA>`\n\n" +
                "Доступные расы: Human, Elf, Dwarf\n" +
                "Доступные классы: Fighter, Barbarian, Sorcerer\n\n" +
                "Пример:\n" +
                "`/character Dima Chirykin Human Fighter Student 10 12 14 8 10 16`";
    }

    @Override
    public String execute(String args) {
        if (args == null || args.isBlank()) {
            return "Не указаны аргументы.\n\n" + usage();
        }

        String[] parts = args.trim().split("\\s+");

        if (parts.length != 11) {
            return "Неверное количество аргументов (нужно 11, получено " + parts.length + ").\n\n" + usage();
        }

        try {
            String name = parts[0];
            String surname = parts[1];
            String race = parts[2];
            String charClass = parts[3];
            String role = parts[4];

            int str  = parseInt(parts[5],  "STR");
            int dex  = parseInt(parts[6],  "DEX");
            int con  = parseInt(parts[7],  "CON");
            int intl = parseInt(parts[8],  "INT");
            int wis  = parseInt(parts[9],  "WIS");
            int cha  = parseInt(parts[10], "CHA");

            Character character = new Character(name, surname, race, charClass, role,
                    str, dex, con, intl, wis, cha);

            return "Персонаж успешно создан\n\n" + character.toString();

        } catch (NumberFormatException e) {
            return "Ошибка: характеристики должны быть целыми числами.\n" + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "Ошибка: " + e.getMessage();
        } catch (Exception e) {
            return "Непредвиденная ошибка: " + e.getMessage();
        }
    }

    private int parseInt(String value, String statName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Поле " + statName + " = '" + value + "' не является числом.");
        }
    }

}