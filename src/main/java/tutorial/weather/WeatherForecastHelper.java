package tutorial.weather;

import org.json.JSONObject;
import tutorial.BotMode;
import tutorial.apirequest.APIRequestData;
import tutorial.apirequest.APIRequestGenerator;
import tutorial.apirequest.APIRequestSender;
import tutorial.geo.Coordinates;

import java.util.HashMap;

public class WeatherForecastHelper {

    private Coordinates coordinates;

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    String getWeatherForecast(int days) throws Exception {
        String weatherForecast = "";

        var parameters = createAPIParameters();
        var requestData = new APIRequestData(BotMode.WEATHER, WeatherEndpoint.FORECAST, parameters);
        var requestLink = APIRequestGenerator.generate(requestData);
        var responseJSON = (JSONObject) APIRequestSender.send(requestLink);

        var weatherDaysJSON = responseJSON.getJSONArray("list");

        for (int currentDay = 0; currentDay <= days; currentDay++) {
            var dayInTicks = currentDay * 8;
            var weatherDayJSON = weatherDaysJSON.getJSONObject(currentDay == 5 ? dayInTicks - 1 : dayInTicks);
            var weatherDTO = WeatherParser.parse(weatherDayJSON);

            weatherForecast += weatherDTO.text() + "\n\n";
        }

        return weatherForecast;
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
}
