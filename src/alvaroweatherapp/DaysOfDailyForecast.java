/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alvaroweatherapp;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Alvaro
 */
public class DaysOfDailyForecast {

    // This is the user API key necessary to get the Json response.
    String myAPIkey = "sOininHGfREQNcZQBhLNGJ9J8YnUCGG2";

    //Get key location
    //url=http://dataservice.accuweather.com/locations/v1/cities/search?apikey=sOininHGfREQNcZQBhLNGJ9J8YnUCGG2&q=Santander
    
    
    
    
    
    
    
    
    /**
     * getKeyLocation: This function get the location key necessary to get the location forecast.
     * Using getJson function
     * 
     * @param cityName
     * @return
     * @throws IOException 
     */
    public String getKeyLocation(String cityName) throws IOException {  
        URL url = new URL("https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + myAPIkey + "&q=" + cityName);
        String locationKey = getJson(url).getString("Key");
        return locationKey;
    }

    
    /**
     * getJson: This function convert the json String received from a url to json Objet
     * Using org.json and commons-io libraries
     * 
     * @param url
     * @return
     * @throws IOException 
     */
    public static JSONObject getJson(URL url) throws IOException {
        String json = IOUtils.toString(url, Charset.forName("UTF-8"));
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObjet = jsonArray.getJSONObject(0);
        return jsonObjet;
    }

}
