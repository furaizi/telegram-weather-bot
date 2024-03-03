package bot.main;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import bot.weather.WeatherBot;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Bot extends TelegramLongPollingBot {

    private static WeatherBot weatherBot;


    private final HashMap<Long, BotUser> users = new HashMap<>();

    private static final ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
    private static final String BOT_USERNAME = "Test bot 1";
    private static final String BOT_TOKEN = "";
    private static final String GREETING =
            """
            Вітаю! Я бот для показу прогнозу погоди.
            Ви можете користуватись ботом за допомогою кнопок знизу.
            Щоб отримати прогноз, спочатку ви повинні вказати назву міста.
            """;
    private static final String WRONG_COMMAND =
            """
            Введена неправильна команда.
            Ознайомтесь зі списком дійсних команд зліва у меню.
            """;



    @Override
    public String getBotUsername() { return BOT_USERNAME; }

    @Override
    public String getBotToken() { return BOT_TOKEN; }

    public HashMap<Long, BotUser> getUsers() {
        return users;
    }


    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var id = msg.getFrom().getId();

        var user = getUserOrAdd(id);
        user.setMessage(msg);

        System.out.printf("%s: %s%n", msg.getFrom().getFirstName(), msg.getText());

        initializeWeatherBot(user);
        initializeKeyboard(user);

        if (msg.isCommand()) {
            switch (msg.getText()) {
                case "/start" -> sendText(user, GREETING);
                default -> sendText(user, WRONG_COMMAND);
            }
        } else if (msg.hasText()) {
            try {
                weatherBot.start();
            } catch (Exception e) {
                sendText(user, e.getMessage());
            }
        }
    }


    public void sendText(BotUser user, String response) {
        SendMessage sm = SendMessage.builder()
                .chatId(user.getId().toString())
                .replyMarkup(user.getKeyboard())
                .text(response).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeKeyboard(BotUser user) {
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        keyboardRow.add(new KeyboardButton("Змінити місцеположення"));
        keyboardRow.add(new KeyboardButton("Моє чинне місцеположення"));
        keyboardRow.add(new KeyboardButton("Дізнатись прогноз"));
        user.setKeyboard(keyboardRows);
    }

    private void initializeWeatherBot(BotUser user) {
        if (weatherBot == null)
            weatherBot = new WeatherBot(user);
        else
            weatherBot.setUser(user);
    }

    private BotUser getUserOrAdd(Long id) {
        if (!users.containsKey(id))
            users.put(id, new BotUser(id));
        return users.get(id);
    }

}
