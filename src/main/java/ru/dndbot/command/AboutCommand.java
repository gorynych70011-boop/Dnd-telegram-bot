package ru.dndbot.command;

public class AboutCommand implements Command {
    @Override
    public String name() {
        return "about";
    }

    @Override
    public String shortDescription() {
        return "описание бота";
    }

    @Override
    public String usage() {
        return "/about — рассказывает, для чего нужен бот и как в нём играть.";
    }

    @Override
    public String execute(String args) {
        return """
                Текстовая ролевая игра по мотивам D&D.

                • Создай своего персонажа (раса, класс, характеристики) или возьми готового.
                • Проходи кампанию: в каждой сцене ты выбираешь действие.
                • Исход действий решают броски кубиков с учётом характеристик героя.""";
    }
}