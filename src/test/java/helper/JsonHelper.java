package helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
public class JsonHelper {
    public static JSONObject readJson(String file){
        JSONParser jsonP = new JSONParser();
        try {
            String path = "src/test/resources/" + file +".json";
            return (JSONObject)jsonP.parse(new FileReader(path));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static JSONArray getJSONArray(String fileName, String variable){
        JSONObject json = readJson(fileName);
        JSONArray var = (JSONArray) json.get(variable);
        return var;
    }

    public static String getString(String fileName, String variable){
        JSONObject json = readJson(fileName);
        String var = (String) json.get(variable);
        return var;
    }

    public static JSONObject getJSONObject(String fileName, String variable){
        JSONObject json = readJson(fileName);
        JSONObject var = (JSONObject) json.get(variable);
        return var;
    }
}
