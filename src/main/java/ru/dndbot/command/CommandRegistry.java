package ru.dndbot.command;

import java.util.*;

public class CommandRegistry {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    public void register(Command command){
        commands.put(command.name(), command);
    }

    public Optional<Command> find(String name){
        return Optional.ofNullable(commands.get(name.toLowerCase()));
    }
    public Collection<Command> all(){
        return Collections.unmodifiableCollection(commands.values());
    }
}
