package bot.weather;

import org.json.JSONObject;
import bot.main.BotMode;
import bot.apirequest.APIRequestData;
import bot.apirequest.APIRequestGenerator;
import bot.apirequest.APIRequestSender;
import bot.geo.Coordinates;
import java.util.HashMap;

public class CurrentWeatherHelper {

    private Coordinates coordinates;

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    String getCurrentWeather() throws Exception {
        var parameters = createAPIParameters();
        var requestData = new APIRequestData(BotMode.WEATHER, WeatherEndpoint.CURRENT, parameters);
        var requestLink = APIRequestGenerator.generate(requestData);
        var weatherJSON = (JSONObject) APIRequestSender.send(requestLink);
        var weatherDTO = WeatherParser.parse(weatherJSON);

        return weatherDTO.text();
    }

    private HashMap<String, String> createAPIParameters() {
        HashMap<String, String> apiParameters = new HashMap<>() {{
            put("lon", String.valueOf(coordinates.longitude));
            put("lat", String.valueOf(coordinates.latitude));
            put("lang", "ua");
            put("units", "metric");
        }};

        return apiParameters;
    }
}
