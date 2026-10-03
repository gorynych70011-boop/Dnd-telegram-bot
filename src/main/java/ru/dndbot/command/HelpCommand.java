package ru.dndbot.command;

public class HelpCommand implements Command {
    private final CommandRegistry registry;

    public HelpCommand(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String shortDescription() {
        return "список команд или справка по команде";
    }

    @Override
    public String usage() {
        return """
                /help — список всех команд
                /help <команда> — подробная справка по команде
                Пример: /help about""";
    }

    @Override
    public String execute(String args) {
        if (args.isBlank()) {
            StringBuilder sb = new StringBuilder("Доступные команды:\n");
            for (Command c : registry.all()) {
                sb.append('/').append(c.name())
                        .append(" — ").append(c.shortDescription())
                        .append('\n');
            }
            sb.append("\nПодробнее: /help <команда>");
            return sb.toString();
        }

        String name = args.trim().split("\\s+")[0];
        if (name.startsWith("/")) {
            name = name.substring(1);          // допускаем и /help /about
        }

        return registry.find(name)
                .map(Command::usage)
                .orElse("Команда «" + name + "» не найдена. Список команд: /help");
    }
}
