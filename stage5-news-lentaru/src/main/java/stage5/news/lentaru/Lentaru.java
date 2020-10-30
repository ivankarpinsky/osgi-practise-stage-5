package stage5.news.lentaru;

import stage5.news.service.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Lentaru implements News {
    public final String url = "https://api.lenta.ru/lists/latest";
    public String name = "Lenta.ru";

    public String[] getNews() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ArrayList<String> allWords = new ArrayList<String>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
            JSONArray headlines= (JSONArray) jsonObject.get("headlines");
            for(int i=0; i<headlines.size(); i++){
                JSONObject headline = (JSONObject) headlines.get(i);
                JSONObject info = (JSONObject) headline.get("info");
                String title = (String) info.get("title");
                String[] words = title.split(" ");
                allWords.addAll(Arrays.asList(words));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return allWords.toArray(new String[0]);
    }

    public String getSourceName() {
        return name;
    }
}
