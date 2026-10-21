package ru.dndbot.command;

import ru.dndbot.core.Check;

import java.io.IOException;

public class RollCommand implements Command {
    @Override
    public String name() {
        return "roll";
    }

    @Override
    public String shortDescription() {
        return "бросок кости d20";
    }

    @Override
    public String usage() {
        return "Бросок кости d20\n" +
                "`/roll` — бросок без модификатора\n" +
                "`/roll <модификатор>` — бросок с модификатором (может быть отрицательным)\n\n" +
                "Пример:\n" +
                "`/roll 5`\n" +
                "`/roll -2`";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        int modifier = 0;
        if (args != null && !args.isBlank()) {
            try {
                modifier = Integer.parseInt(args.trim().split("\\s+")[0]);
            } catch (NumberFormatException e) {
                return "Ошибка: модификатор должен быть целым числом.\n" + usage();
            }
        }

        int dice = Check.roll();
        int total = dice + modifier;

        if (modifier == 0) {
            return "Бросок d20: **" + dice + "**";
        }
        String sign = modifier > 0 ? "+" : "";
        return "Бросок d20: **" + dice + "** " + sign + modifier + " = **" + total + "**";
    }
}
