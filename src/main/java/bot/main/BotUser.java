package bot.main;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import bot.geo.Coordinates;
import bot.weather.WeatherCondition;

import java.util.ArrayList;

import static bot.main.Main.bot;

public class BotUser extends User {
    private boolean hasLocation = false;
    private String location = null;
    private Coordinates coordinates;
    private final Long id;
    private Message message;
    public WeatherCondition weatherCondition = WeatherCondition.START;
    private ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

    public BotUser(Long id, Message message) {
        this.id = id;
        this.message = message;
    }

    public BotUser(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() { return id; }

    public Message getMessage() { return message; }

    public void setMessage(Message msg) { this.message = msg; }

    public boolean hasLocation() {
        return hasLocation;
    }

    public void setHasLocation(boolean hasLocation) {
        this.hasLocation = hasLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ReplyKeyboardMarkup getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(ArrayList<KeyboardRow> keyboard) {
        this.keyboard.setKeyboard(keyboard);
    }

    public void sendLocation() {
        if (hasLocation)
            bot.sendText(this, "Ваше місто: " + getLocation());
        else
            bot.sendText(this, "Ви ще не вказали місто!");
    }
}
