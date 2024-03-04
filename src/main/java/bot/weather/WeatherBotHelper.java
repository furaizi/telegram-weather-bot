package bot.weather;

import bot.main.BotUser;
import bot.geo.GeoHelper;

import static bot.main.Main.bot;

class WeatherBotHelper {



    // Bot buttons text
    private static final String CHANGE_LOCATION = "Змінити місцеположення";
    private static final String CURRENT_LOCATION = "Моє чинне місцеположення";
    private static final String GET_FORECAST = "Дізнатись прогноз";

    // Bot answers text
    private static final String PLEASE_ENTER_CITY = "Введіть, будь ласка, ваше місто.";
    private static final String ENTER_CITY_FIRST = "Для використання цієї функції вам спочатку треба вказати місто.";
    private static final String GET_FORECAST_RULES = "Введіть кількість днів від 0 до 5, прогноз на які ви хочете.\n" +
            "0 - якщо на зараз.";
    private static final String CHOOSE_BUTTON = "Оберіть одну з кнопок нижче.";
    private static final String NOT_FROM_0_TO_5 = "Введіть, будь ласка, число від 0 до 5.";
    private static final String NOT_A_NUMBER = "Введіть, будь ласка, число.";

    private BotUser user;
    private String message;
    private GeoHelper geoHelper;
    private CurrentWeatherHelper currentWeatherHelper = new CurrentWeatherHelper();
    private WeatherForecastHelper weatherForecastHelper = new WeatherForecastHelper();

    public WeatherBotHelper(BotUser user) {
        this.user = user;
        this.message = user.getMessage().getText();
        this.geoHelper = new GeoHelper(message);
    }

    private void updateMessage() {
        message = user.getMessage().getText();
        geoHelper.setLocation(message);
    }

    void handleLocation() throws Exception {
        updateMessage();

        if (message.equals(CHANGE_LOCATION))
            throw new WeatherException(PLEASE_ENTER_CITY);
        else if (message.equals(CURRENT_LOCATION))
            user.sendLocation();
        else {
            var coordinates = geoHelper.getCoordinates();

            user.setCoordinates(coordinates);
            user.setLocation(message);
            user.setHasLocation(true);

            bot.sendText(user, "Ви встановили місто \"" + user.getLocation() + "\".");
            bot.sendText(user, String.format("Ви встановили місто \"%s\"", user.getLocation()));
            user.weatherCondition = WeatherCondition.START;
        }
    }

    void handleWeather() throws Exception {
        updateMessage();

        try {
            int days = Integer.parseInt(message);
            if (isCurrent(days)) {
                currentWeatherHelper.setCoordinates( user.getCoordinates() );
                bot.sendText(user, currentWeatherHelper.getCurrentWeather());
                user.weatherCondition = WeatherCondition.START;
            } else if (isForecast(days)) {
                weatherForecastHelper.setCoordinates( user.getCoordinates() );
                bot.sendText(user, weatherForecastHelper.getWeatherForecast(days));
                user.weatherCondition = WeatherCondition.START;
            } else
                throw new WeatherException(NOT_FROM_0_TO_5);
        } catch (NumberFormatException e) {
            throw new WeatherException(NOT_A_NUMBER);
        }
    }

    void handleStart() throws Exception {
        updateMessage();

        switch (message) {
            case CHANGE_LOCATION -> {
                handleLocation();
                user.weatherCondition = WeatherCondition.LOCATION;
            }
            case CURRENT_LOCATION -> user.sendLocation();
            case GET_FORECAST -> {
                if (user.hasLocation()) {
                    bot.sendText(user, GET_FORECAST_RULES);
                    user.weatherCondition = WeatherCondition.WEATHER;
                }
                else
                    throw new WeatherException(ENTER_CITY_FIRST);
            }
            default -> throw new WeatherException(CHOOSE_BUTTON);
        }
    }

    private boolean isCurrent(int days) {
        return days == 0;
    }

    private boolean isForecast(int days) {
        return days > 0 && days <= 5;
    }

}
