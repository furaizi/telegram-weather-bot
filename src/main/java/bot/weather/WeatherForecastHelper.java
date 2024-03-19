package bot.weather;

import org.json.JSONArray;
import org.json.JSONObject;
import bot.main.BotMode;
import bot.apirequest.APIRequestData;
import bot.apirequest.APIRequestGenerator;
import bot.apirequest.APIRequestSender;
import bot.geo.Coordinates;

import java.util.HashMap;

public class WeatherForecastHelper {

    private Coordinates coordinates;

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    String getWeatherForecast(int days) throws Exception {
        var parameters = createAPIParameters();
        var requestData = new APIRequestData(BotMode.WEATHER, WeatherEndpoint.FORECAST, parameters);
        var requestLink = APIRequestGenerator.generate(requestData);
        var responseJSON = (JSONObject) APIRequestSender.send(requestLink);
        var weatherDaysJSON = responseJSON.getJSONArray("list");

        return parseWeatherForecast(weatherDaysJSON, days);
    }

    private HashMap<String, String> createAPIParameters() {
        final String MAX_FORECAST_NUMBER = "40";
        HashMap<String, String> apiParameters = new HashMap<>() {{
            put("lon", String.valueOf(coordinates.longitude));
            put("lat", String.valueOf(coordinates.latitude));
            put("cnt", MAX_FORECAST_NUMBER);
            put("lang", "ua");
            put("units", "metric");
        }};

        return apiParameters;
    }

    private String parseWeatherForecast(JSONArray json, int days) {
        StringBuilder weatherForecast = new StringBuilder();

        for (int currentDay = 0; currentDay <= days; currentDay++) {
            var dayInTicks = currentDay * 8;
            var weatherDayJSON = json.getJSONObject(currentDay == 5 ? dayInTicks - 1 : dayInTicks);
            var weatherDTO = WeatherParser.parse(weatherDayJSON);

            weatherForecast
                    .append(weatherDTO.text())
                    .append("\n\n");
        }

        return weatherForecast.toString();
    }
}
