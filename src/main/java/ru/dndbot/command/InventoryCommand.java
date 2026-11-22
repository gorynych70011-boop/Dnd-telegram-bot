package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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

        StringBuilder sb = new StringBuilder("Экипировка:  \n");

        Map<EquipmentSlot, Item> equippedItems = player.character.getEquipment().getAllEquipped();

        if (equippedItems.isEmpty()){
            sb.append("• (пусто)\n");
        }else{
            for (Map.Entry<EquipmentSlot, Item> entry : equippedItems.entrySet()){
                String slotName = switch (entry.getKey()){
                    case MAIN_HAND -> "Оружие";
                    case ARMOR -> "Броня";
                    case HELMET -> "Шлем";
                    case NULL -> "Сыры, зелья и т.д.";
                };
                sb.append(slotName).append(": ").append(entry.getValue().toString()).append("\n");
            }
        }
        sb.append("\n Инвентарь: \n");

        for (Item item : inv.getItems()){
            sb.append("• ").append(item.toString()).append("\n");
        }
        return sb.toString();
    }
}
