package bot.weather;

import bot.main.Endpoint;

public enum WeatherEndpoint implements Endpoint {
    CURRENT("weather"),
    FORECAST("forecast");

    private String endpoint;
    WeatherEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return endpoint;
    }
}
