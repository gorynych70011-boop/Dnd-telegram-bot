package ru.dndbot.telegram;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.dndbot.core.BotLogic;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer{
    private final TelegramClient client;
    private final BotLogic logic;

    public TelegramBot(String token, BotLogic logic) {
        this.client = new OkHttpTelegramClient(token);
        this.logic = logic;
    }
    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String chatID = String.valueOf(update.getMessage().getChatId());
        String reply = logic.handle(update.getMessage().getText());
        try{
            client.execute(new SendMessage(chatID,reply));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
}
