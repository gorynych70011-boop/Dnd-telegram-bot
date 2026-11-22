package ru.dndbot.command;

import ru.dndbot.core.Character;
import ru.dndbot.core.Player;
import ru.dndbot.core.PlayerRead;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfileCommand implements Command{
    @Override
    public String name(){return "profile"; }

    @Override
    public String shortDescription(){return "информация о персонаже";}

    @Override
    public String usage(){return "Просмотр профиля персонажа\n" +
            "`/profile` - показать все характеристики"; }

    @Override
    public String execute(String args, long telegramId) throws IOException {
        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if (player == null){return "Сначала создайте персонажа!";}

        Character c = player.character;
        StringBuilder sb = new StringBuilder();

        player.character.setInventory(player.inventory);

        sb.append("Пофиль персонажа\n\n");
        sb.append("Имя: ").append(c.getName()).append(" ").append(c.getSurname()).append("\n");
        sb.append("Раса: ").append(c.getRace()).append(" | Класс: ").append(c.getCharacterClass()).append("\n");
        sb.append("Роль: ").append(c.getRole()).append("\n\n");

        sb.append("Характеристики (с учетом экипировки): \n");
        sb.append("HP: ").append(c.getHp()).append("\n");
        sb.append("AC (Броня): ").append(c.getArmor()).append("\n");
        sb.append("Сила (STR): ").append(c.getStrength()).append("\n");
        sb.append("Ловкость (DEX): ").append(c.getDexterity()).append("\n");
        sb.append("Телосложение (CON): ").append(c.getConstitution()).append("\n");
        sb.append("Интелект (INT): ").append(c.getIntelligence()).append("\n");
        sb.append("Мудрость (WIS): ").append(c.getWisdom()).append("\n");
        sb.append("Харизма (CHA): ").append(c.getCharisma()).append("\n\n");

        return sb.toString();
    }
}
