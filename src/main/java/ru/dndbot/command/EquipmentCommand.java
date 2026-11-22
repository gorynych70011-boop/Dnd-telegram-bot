package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

public class EquipmentCommand implements Command{
    @Override
    public String name() {
        return "equipment";
    }

    @Override
    public String shortDescription() {
        return "просмотр и снятие экипировки";
    }

    @Override
    public String usage() {
        return "Просмотр надетых вещей\n" +
                "`/equipment` - показать, что надето\n" +
                "`/equipment unequip <Название>` - снять предмет и положить в инвентарь\n\n" +
                "Пример: \n" +
                "`/equipment unequip Ржавый кинжал`";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if (player == null){
            return "Сначала создайте персонажа!";
        }

        Equipment equipment = player.character.getEquipment();

        if (args != null && args.trim().toLowerCase().startsWith("unequip")){
            String itemName = args.trim().substring(7).trim();

            if(itemName.isEmpty()){
                return "Укажите название предмета, который хотите снять. \nПример: `/equipment unequip Ржавый кинжал`";
            }
            EquipmentSlot targetSlot = null;
            Item itemToRemove = null;

            for(Map.Entry<EquipmentSlot, Item> entry : equipment.getAllEquipped().entrySet()){
                if(entry.getValue().getName().equalsIgnoreCase(itemName)){

                    targetSlot = entry.getKey();
                    itemToRemove = entry.getValue();
                    break;
                }
            }
            if(itemToRemove == null){
                return "Вы не носите предмет с название '" + itemName + "'.";
            }
            equipment.unequip(targetSlot);

            player.inventory.addItem(itemToRemove);

            Player.save(player, playerDir);

            return "Вы сняли " + itemToRemove.getName() + "и положили его в инвентарь.\n";
        }

        StringBuilder sb = new StringBuilder("Текущая экипировка: \n\n");

        Map<EquipmentSlot, Item> equippedItems = player.character.getEquipment().getAllEquipped();

        if (equippedItems.isEmpty()){
            sb.append("У вас ничего не надето. Загляните в `/inventory` и используйте `/equip <название>`.");
            return sb.toString();
        }

        for (Map.Entry<EquipmentSlot, Item> entry : equippedItems.entrySet()){
            String slotName = switch (entry.getKey()){
                case MAIN_HAND -> "Оружие";
                case ARMOR -> "Броня";
                case HELMET -> "Шлем";
                case NULL -> "Сыры, зелья и т.д.";
            };
            sb.append(slotName).append(": ").append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
