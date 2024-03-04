package bot.geo;

import bot.weather.WeatherException;
import org.json.JSONArray;

public class LocationParser {

    static Coordinates parse(JSONArray locationsJSON) throws WeatherException {
        final var FIRST = 0;
        if (isEmpty(locationsJSON))
            throw new WeatherException("Такого міста не існує");

        var firstLocationJSON = locationsJSON.getJSONObject(FIRST);

        double longitude = firstLocationJSON.getDouble("lon");
        double latitude = firstLocationJSON.getDouble("lat");

        return new Coordinates(longitude, latitude);
    }

    private static boolean isEmpty(JSONArray json) {
        return json.toString().equals("[]");
    }
}
