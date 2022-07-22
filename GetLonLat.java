import java.net.*;
import java.util.*;
import org.json.*;

public class GetLonLat {
    public List<JSONObject> jsonCityArray;
    GetLonLat(String location, String key) throws MalformedURLException
    {
        jsonCityArray = new ArrayList<>();
        String weather = "";
        try{
            URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + location + "&limit=5&appid=" + key);
            Scanner input = new Scanner(url.openStream());
            while(input.hasNext())
            {
                weather += input.nextLine();
            }
            input.close();
            JSONArray main = new JSONArray(weather);
            main.length();
            for(int i = 0; i < main.length(); i++)
            {
                jsonCityArray.add(main.getJSONObject(i));
            }
        }
        catch(Exception e)
        {
            System.out.println("Error getting city");
        }
    }
}
