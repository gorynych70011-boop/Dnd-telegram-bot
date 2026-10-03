package ru.dndbot.command;

public interface Command {
    String name();

    String shortDescription();

    String usage();

    String execute(String args);
}
