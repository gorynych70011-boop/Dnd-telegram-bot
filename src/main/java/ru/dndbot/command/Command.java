package ru.dndbot.command;

import java.io.IOException;

public interface Command {
    String name();

    String shortDescription();

    String usage();

    default String execute(String args, long telegramId) throws IOException{
        return null;
    }

    default String execute(String args) throws IOException {
        return execute(args, 0L);
    }
}
