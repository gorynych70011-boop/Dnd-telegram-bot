package ru.dndbot.command;

import ru.dndbot.core.Character;
import ru.dndbot.core.Player;

import java.io.IOException;

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
    public String execute(String args, long telegramId) throws IOException {

        if (args == null || args.isBlank()) {
            return """
                В меню вы можете:
                •Создать своего персонажа
                •Выбрать из списка готовых

                0 - создать самому 
                1 - выбрать первого
                2 - выбрать второго
                3 - выбрать третьего""";
        }

        String[] parts = args.trim().split("\\s+");

        if (parts.length == 1 && parts[0].equals("0")) {
            return usage();
        }

        // Если пользователь выбрал готового персонажа
        if (parts.length == 1 && (parts[0].equals("1") || parts[0].equals("2") || parts[0].equals("3"))) {
            Character c;
            if (parts[0].equals("1")) {
                c = new Character("A", "B", "Fighter", "Human", "Abc", 1, 2, 3, 4, 5, 6);
            } else if (parts[0].equals("2")) {
                c = new Character("C", "D", "Barbarian", "Gnom", "Qwer", 6, 5, 4, 3, 2, 1);
            } else {
                c = new Character("E", "F", "Sorcerer", "XZ", "WASD", 10, 10, 10, 10, 10, 10);
            }

            Player.save(new Player(telegramId, c));
            System.out.println(">>> Сохранен готовый персонаж для ID: " + telegramId);
            return "Выбран персонаж: " + c.toString();
        }

        // Если введены кастомные характеристики
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

            // ВОТ ГЛАВНАЯ СТРОКА СОХРАНЕНИЯ:
            Player.save(new Player(telegramId, character));

            System.out.println(">>> Кастомный персонаж успешно сохранен для ID: " + telegramId);

            return "Персонаж успешно создан\n\n" + character.toString();

        } catch (ArrayIndexOutOfBoundsException e) {
            return "Ошибка: вы ввели слишком мало параметров. Проверьте пример в /character.";
        } catch (NumberFormatException e) {
            return "Ошибка: характеристики должны быть целыми числами.\n" + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "Ошибка: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace(); // Выведет ошибку в консоль!
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