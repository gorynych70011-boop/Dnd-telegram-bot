package ru.dndbot.command;

import ru.dndbot.core.Player;
import ru.dndbot.core.PlayerRead;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DropCommand implements Command{
    @Override
    public String name(){return "drop"; }

    @Override
    public String shortDescription(){return "выбросить предмет"; }

    @Override
    public String usage(){return "Выбросить предмет из инвентаря\n" +
            "`/drop <Название предмета>`\n\n" +
            "Пример:\n" +
            "`/drop Странный камень`";}

    @Override
    public String execute(String args, long telegramId) throws IOException{
        if(args == null || args.isBlank()){return usage();}

        Path playerDir = Paths.get("players");
        Player player = PlayerRead.findByTelegramId(telegramId, playerDir);

        if(player == null){return "Сначала создайте персонажа!"; }

        String itemName = args.trim();
        boolean removed = player.inventory.removeItem(itemName);

        if(removed){
            Player.save(player, playerDir);
            return "Вы выбросили '" + itemName + "' из инвентаря";
        }else {
            return "У вас нет предмета с названием '" + itemName + "' в инвентаре.";
        }
    }
}
