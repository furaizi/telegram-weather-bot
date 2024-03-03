package tutorial.geo;

import org.json.JSONArray;
import tutorial.BotMode;
import tutorial.apirequest.APIRequestData;
import tutorial.apirequest.APIRequestGenerator;
import tutorial.apirequest.APIRequestSender;

import java.util.HashMap;

public class GeoHelper {

    private String location;

    public GeoHelper(String location) {
        this.location = location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Coordinates getCoordinates() throws Exception {
        var parameters = createAPIParameters();
        var requestData = new APIRequestData(BotMode.GEO, GeoEndpoint.DIRECT, parameters);
        var requestLink = APIRequestGenerator.generate(requestData);
        var cityNamesJSON = (JSONArray) APIRequestSender.send(requestLink);
        var coordinates = LocationParser.parse(cityNamesJSON);

        return coordinates;
    }

    private HashMap<String, String> createAPIParameters() {
        final String MAX_LOCATIONS = "5";
        HashMap<String, String> apiParameters = new HashMap<>() {{
            put("q", location);
            put("limit", MAX_LOCATIONS);
        }};

        return apiParameters;
    }
}
