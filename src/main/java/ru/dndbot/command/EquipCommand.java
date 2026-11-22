package ru.dndbot.command;

import ru.dndbot.core.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EquipCommand implements Command {
    @Override
    public String name() {
        return "equip";
    }

    @Override
    public String shortDescription() {
        return "надеть предмет";
    }

    @Override
    public String usage() {
        return "Надеть броню или оружие\n" +
                "`/equip <Название предмета>`\n\n" +
                "Пример:\n" +
                "`/equip Ржавый кинжал`";
    }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        if (args == null || args.isBlank()) return usage();

        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if (player == null) return "Сначала создайте персонажа!";

        String itemName = args.trim();
        Item itemToEquip = null;

        // 1. Ищем предмет в инвентаре
        for (Item item : player.inventory.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToEquip = item;
                break;
            }
        }

        if (itemToEquip == null) {
            return "У вас нет предмета '" + itemName + "' в инвентаре.";
        }

        if (!itemToEquip.isEquippable()) {
            return "Этот предмет нельзя надеть (это не броня и не оружие).";
        }

        Equipment equipment = player.character.getEquipment();
        EquipmentSlot targetSlot = itemToEquip.getSlot();


        Item oldItem = equipment.getEquipped();

        Item currentlyEquipped = equipment.getAllEquipped().get(targetSlot);

        if (currentlyEquipped != null) {
            equipment.getAllEquipped().remove(targetSlot);
            currentlyEquipped.setEquipped(false);
            player.inventory.addItem(currentlyEquipped);
        }


        player.inventory.removeItem(itemToEquip.getName());

        equipment.equip(itemToEquip);

        Player.save(player, playerDir);

        StringBuilder result = new StringBuilder("Вы экипировали **" + itemToEquip.getName() + "**!\n\n");

        if (currentlyEquipped != null) {
            result.append("Предмет ").append(currentlyEquipped.getName()).append(" снят и возвращен в инвентарь.\n\n");
        }

        result.append("Ваш AC (защита): ").append(player.character.getArmor());
        result.append("\nУрон: ").append(player.character.calculateDamage());

        return result.toString();
    }
}