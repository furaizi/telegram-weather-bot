package tutorial.apirequest;

import tutorial.BotMode;
import tutorial.Endpoint;

import java.util.HashMap;

public class APIRequestData {

    private BotMode mode;

    private Endpoint endpoint;

    private HashMap<String, String> parameters;

    public APIRequestData(BotMode mode, Endpoint endpoint, HashMap<String, String> parameters) {
        this.mode = mode;
        this.endpoint = endpoint;
        this.parameters = parameters;
    }

    public BotMode getMode() {
        return mode;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

}
