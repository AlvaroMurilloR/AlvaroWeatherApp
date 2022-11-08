/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alvaroweatherapp;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alvaro
 */
public class DaysOfDailyForecast {

    // This is the user API key necessary to get the Json response.
    private final String myAPIkey = "AEn4U8W1ZnBc5Ag1dRt3oW4KgjshB4L6";
    // This boolean is to know if the json to receive is for get the location key
    private static boolean getJsonForLocation = false;

    /**
     * 
     * This function get the current weather of the City that the user insert in the console.
     * 
     * @param cityName
     * @param countryCode
     * @param measureUnit
     * @return
     * @throws IOException
     * @throws ParseException 
     */
    public String getCurrentWeather(String cityName, String countryCode, String measureUnit) throws IOException, ParseException {
        URL url = new URL("https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + getKeyLocation(cityName, countryCode) + "?apikey=" + myAPIkey);

        // Getting date
        String stringDate = getJson(url).getJSONArray("DailyForecasts").getJSONObject(0).getString("Date").substring(0, 10); // Getting the date of the json
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate); // Converting the string date to Date format
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy"); // Changing the format date
        stringDate = formatter.format(date);

        // Getting weather
        String weather = getJson(url).getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Day").getString("IconPhrase"); // Getting the weather

        //Getting temperature
        BigDecimal minTemperature = getJson(url).getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getBigDecimal("Value");
        BigDecimal maxTemperature = getJson(url).getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getBigDecimal("Value");
        Float temperature = (minTemperature.floatValue() + maxTemperature.floatValue()) / 2;

        // Writing the response
        String response = "$ AlvaroWeatherApp current " + cityName + "," + countryCode;
        if (measureUnit.equals("3")) {
            response = response + " --units=imperial";
        } else {
            temperature = (temperature - 32) / 1.8f;
        }
        response = response + "\n" + cityName.toUpperCase() + " (" + countryCode + ")"
                + "\n" + stringDate
                + "\n> Weather: " + weather
                + "\n> Temperature: " + Math.round(temperature * 100.0) / 100.0;;
        if (measureUnit.equals("3")) {
            response = response + " ºF";
        } else {
            response = response + " ºC";
        }

        return response;
    }

    /**
     * getKeyLocation: This function get the location key necessary to get the
     * location forecast. Using getJson function
     *
     * @param cityName
     * @param countryCode
     * @return
     * @throws IOException
     */
    public String getKeyLocation(String cityName, String countryCode) throws IOException {
        getJsonForLocation = true;
        URL url = new URL("https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + myAPIkey + "&q=" + cityName + "%2C" + countryCode);
        String locationKey = getJson(url).getString("Key");
        return locationKey;
    }

    /**
     * getJson: This function convert the json String received from a url to
     * json Objet Using org.json and commons-io libraries
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static JSONObject getJson(URL url) throws IOException {
        JSONObject jsonObjet;
        String json = IOUtils.toString(url, Charset.forName("UTF-8"));
        // If the Json is received from the getKeyLocation urs, the receibed json is an array, if no, it's not an array.
        if (getJsonForLocation) {
            JSONArray jsonArray = new JSONArray(json);
            jsonObjet = jsonArray.getJSONObject(0);
            getJsonForLocation = false;
        } else {
            jsonObjet = new JSONObject(json);
        }
        return jsonObjet;
    }

}
