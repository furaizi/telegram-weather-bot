package tutorial;

public enum BotMode {
    WEATHER("https://api.openweathermap.org/data/2.5/") {
        public String getAPIKey() { return ""; }
    },
    CURRENCY("https://api.currencyapi.com/v3/") {
        public String getAPIKey() { return ""; }
    },
    GEO("http://api.openweathermap.org/geo/1.0/") {
        public String getAPIKey() { return ""; }
    };

    private String baseLink;

    BotMode(String baseLink) {
        this.baseLink = baseLink;
    }

    public abstract String getAPIKey();

    public String getBaseLink() { return baseLink; }
}
