import java.net.*;
import java.util.*;
import org.json.*;

public class GetWeather {
    GetWeather(String lat, String lon, String key) throws MalformedURLException
    {
        //get api call
        String weather = "";
        try{
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric" + "&appid=" + key);
            Scanner input = new Scanner(url.openStream());
            while(input.hasNext())
            {
                weather += input.nextLine();
            }
            input.close();
            JSONObject whole = new JSONObject(weather);
            JSONObject main = whole.getJSONObject("main");
            String id = whole.getString("name");
            System.out.println(main);
        }
        catch(Exception e)
        {
            System.out.println("Error getting weather");
        }
    }
}
