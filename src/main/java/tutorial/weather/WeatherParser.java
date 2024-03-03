package tutorial.weather;

import org.json.JSONObject;

public class WeatherParser {

    static WeatherDTO parse(JSONObject weatherJSON) {
        JSONObject generalInfo = weatherJSON.getJSONArray("weather").getJSONObject(0);
        JSONObject mainInfo = weatherJSON.getJSONObject("main");
        JSONObject windInfo = weatherJSON.getJSONObject("wind");
        JSONObject cloudsInfo = weatherJSON.getJSONObject("clouds");

        WeatherDTO weatherDTO = new WeatherDTO();

        String description = generalInfo.getString("description");
        double temperature = mainInfo.getDouble("temp");
        double tempFeelsLike = mainInfo.getDouble("feels_like");
        double pressure = mainInfo.getDouble("pressure");
        long humidity = mainInfo.getLong("humidity");
        long cloudiness = cloudsInfo.getLong("all");
        double windSpeed = windInfo.getDouble("speed");

        weatherDTO.description = description;
        weatherDTO.temperature = temperature;
        weatherDTO.tempFeelsLike = tempFeelsLike;
        weatherDTO.pressure = pressure;
        weatherDTO.humidity = humidity;
        weatherDTO.cloudiness = cloudiness;
        weatherDTO.windSpeed = windSpeed;

        return weatherDTO;
    }
}
