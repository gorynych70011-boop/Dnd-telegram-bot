package ru.dndbot.command;

import ru.dndbot.core.Inventory;
import ru.dndbot.core.Item;
import ru.dndbot.core.Player;
import ru.dndbot.core.PlayerRead;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InventoryCommand implements Command {
    @Override
    public String name() {
        return "inventory";
    }

    @Override
    public String shortDescription(){
        return "ваш инвентарь";
    }

    @Override
    public String usage() {
        return "Просмотр инвентаря\n" +
                "`/character` - покзать все предметы\n\n";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException{
        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if (player == null){
            return "Сначала создайте персонажа!";
        }

        Inventory inv = player.inventory;
        if (inv == null || inv.isEmpty()){
            return "Ваш инвентарь пуст. Сразитесь с кем-нибудь, чтобы получить лут!";
        }

        StringBuilder sb = new StringBuilder("Ваш инвентарь:  \n\n");
        for (Item item : inv.getItems()){
            sb.append("• ").append(item.toString()).append("\n");
        }
        return sb.toString();
    }
}
