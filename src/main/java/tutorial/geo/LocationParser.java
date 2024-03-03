package tutorial.geo;

import org.json.JSONArray;

public class LocationParser {

    static Coordinates parse(JSONArray locationsJSON) {
        final var FIRST = 0;
        var firstLocationJSON = locationsJSON.getJSONObject(FIRST);

        double longitude = firstLocationJSON.getDouble("lon");
        double latitude = firstLocationJSON.getDouble("lat");

        return new Coordinates(longitude, latitude);
    }
}
