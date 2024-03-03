package tutorial.geo;

import tutorial.Endpoint;

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
