package bot.weather;

import bot.main.BotUser;
import bot.main.Startable;

public class WeatherBot implements Startable {

    private BotUser user;
    private WeatherBotHelper weatherBotHelper;


    public WeatherBot(BotUser user) {
        this.user = user;
        weatherBotHelper = new WeatherBotHelper(user);
    }

    public void setUser(BotUser user) {
        this.user = user;
    }

    @Override
    public void start() throws Exception {
        switch (user.weatherCondition) {
            case LOCATION -> weatherBotHelper.handleLocation();
            case WEATHER -> weatherBotHelper.handleWeather();
            case START -> weatherBotHelper.handleStart();
        }
    }


}
