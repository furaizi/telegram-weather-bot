package tutorial.apirequest;

import java.util.Map;
import java.util.stream.Collectors;

public class APIRequestGenerator {

    private static String request;
    private static APIRequestData requestData;

    public static String generate(APIRequestData data) {
        request = data.getMode().getBaseLink();
        requestData = data;

        addEndpoint();
        addAPIKey();
        addParameters();

        return request;
    }

    private static void addEndpoint() {
        request += requestData.getEndpoint() + "?";
    }

    private static void addAPIKey() {
        request += requestData.getMode().getAPIKey() + "&";
    }

    private static void addParameters() {
        request += requestData.getParameters()
                .entrySet()
                .stream()
                .map(APIRequestGenerator::makePair)
                .collect(Collectors.joining("&"));
    }

    private static String makePair(Map.Entry<String, String> pair) {
        return pair.getKey() + "=" + pair.getValue();
    }
}
