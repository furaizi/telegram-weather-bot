package bot.apirequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class APIRequestSender {

    public static Object send(String link) throws IOException {
        URL weatherURL = new URL(link);
        URLConnection connection = weatherURL.openConnection();
        BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
        String json = reader.readLine();
        reader.close();

        if (isJSONObject(json))
            return new JSONObject(json);
        else if (isJSONArray(json))
            return new JSONArray(json);
        else
            throw new JSONException("Not a JSON");
    }

    private static boolean isJSONObject(String json) {
        return json.startsWith("{");
    }

    private static boolean isJSONArray(String json) {
        return json.startsWith("[");
    }
}
