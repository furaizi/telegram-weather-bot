package bot.geo;

import bot.main.Endpoint;

public enum GeoEndpoint implements Endpoint {
    DIRECT("direct");

    private String endpoint;
    GeoEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return endpoint;
    }
}
