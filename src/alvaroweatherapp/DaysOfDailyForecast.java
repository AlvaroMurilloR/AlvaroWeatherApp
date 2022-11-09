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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * This class contains all the necessary functions for getting the Daily
 * Forecast
 *
 * @author Alvaro
 */
public class DaysOfDailyForecast {

    // This is the user API key necessary to get the Json response.
    private final String myAPIkey = "NapNaLuAX9WmUk5OIflL40Pb0tiHjVif";
    // This boolean is to know if the json to receive is for get the location key
    private static boolean getJsonForLocation = false;

    /**
     *
     * This function get the current weather for the given location
     *
     * @param cityName
     * @param countryCode
     * @param measureUnit
     * @return
     */
    public String getCurrentWeather(String cityName, String countryCode, String measureUnit) {
        String response = "";
        try {
            String locationKey = getKeyLocation(cityName, countryCode);
            // If the location key is null, city not found
            if (locationKey == null) {
                response = "City Not Found";
            } else {
                //Getting the url
                URL url = new URL("https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + locationKey + "?apikey=" + myAPIkey);

                // Writing the response
                response = "$ AlvaroWeatherApp current " + cityName + "," + countryCode;
                if (measureUnit.equals("3")) {
                    response = response + " --units=imperial";
                }
                response = response + "\n" + cityName.toUpperCase() + " (" + countryCode + ")"
                        + "\n" + getDate(url, 0)
                        + "\n> Weather: " + getWeather(url, 0)
                        + "\n> Temperature: " + Math.round(getTemperature(url, 0, measureUnit) * 100.0) / 100.0; // Rounding to two decimals
                if (measureUnit.equals("3")) {
                    response = response + " ºF";
                } else {
                    response = response + " ºC";
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DaysOfDailyForecast.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    /**
     *
     * This function get the weather forecast for the given location and given
     * number of the days (max 5)
     *
     * @param cityName
     * @param countryCode
     * @param measureUnit
     * @param numDays
     * @return
     */
    public String getWeatherForecastDays(String cityName, String countryCode, String measureUnit, int numDays) {
        String response = "";
        try {
            String locationKey = getKeyLocation(cityName, countryCode);
            // If the location key is null, city not found
            if (locationKey == null) {
                response = "City Not Found";
            } else {
                //Getting the url
                URL url = new URL("https://dataservice.accuweather.com/forecasts/v1/daily/5day/" + locationKey + "?apikey=" + myAPIkey);

                // Writing the response
                response = "$ AlvaroWeatherApp current " + cityName + "," + countryCode + " --days=" + numDays;
                if (measureUnit.equals("3")) {
                    response = response + " --units=imperial";
                }
                response = response + "\n" + cityName.toUpperCase() + " (" + countryCode + ")";
                //Writting the chose days in the response
                for (int i = 0; i < numDays; i++) {
                    response = response + "\n" + getDate(url, i)
                            + "\n> Weather: " + getWeather(url, i)
                            + "\n> Temperature: " + Math.round(getTemperature(url, i, measureUnit) * 100.0) / 100.0; // Rounding to two decimals
                    if (measureUnit.equals("3")) {
                        response = response + " ºF";
                    } else {
                        response = response + " ºC";
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DaysOfDailyForecast.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    /**
     * This function get the location key necessary to get the location
     * forecast. Using getJson function
     *
     * @param cityName
     * @param countryCode
     * @return
     * @throws IOException
     */
    public String getKeyLocation(String cityName, String countryCode) throws IOException {
        String locationKey = null;
        getJsonForLocation = true;
        URL url = new URL("https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + myAPIkey + "&q=" + cityName + "%2C" + countryCode);
        JSONObject locationJson = getJson(url);
        if (locationJson != null) {
            locationKey = locationJson.getString("Key");
        } else {
            return null;
        }
        return locationKey;
    }

    /**
     * This function convert the json String received from a url to json Object
     * Using org.json and commons-io libraries
     *
     * @param url
     * @return
     */
    public static JSONObject getJson(URL url) {
        JSONObject jsonObject = null;
        try {
            String json = IOUtils.toString(url, Charset.forName("UTF-8"));
            // If the Json is received from the getKeyLocation urs, the receibed json is an array, if no, it's not an array.
            if (getJsonForLocation) {
                getJsonForLocation = false;
                JSONArray jsonArray = new JSONArray(json);
                if (jsonArray.isEmpty()) {
                    return null;
                } else {
                    jsonObject = jsonArray.getJSONObject(0);
                }
            } else {
                jsonObject = new JSONObject(json);
            }

        } catch (IOException | JSONException ex) {
            Logger.getLogger(DaysOfDailyForecast.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;
    }

    /**
     *
     * This function get the date of the day
     *
     * @param url
     * @param numDays
     * @return
     */
    public String getDate(URL url, int numDays) {
        String stringDate = "";
        try {
            stringDate = getJson(url).getJSONArray("DailyForecasts").getJSONObject(numDays).getString("Date").substring(0, 10); // Getting the date of the json
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate); // Converting the string date to Date format
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy"); // Changing the format date
            stringDate = formatter.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(DaysOfDailyForecast.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stringDate;
    }

    /**
     * This function get the weather state (Cloudy, Sunny...) of the day
     *
     * @param url
     * @param numDays
     * @return
     */
    public String getWeather(URL url, int numDays) {
        String weather;
        weather = getJson(url).getJSONArray("DailyForecasts").getJSONObject(numDays).getJSONObject("Day").getString("IconPhrase"); // Getting the weather
        return weather;
    }

    /**
     *
     * This function get the average temperature of the day
     *
     * @param url
     * @param numDays
     * @param measureUnit
     * @return
     */
    public Float getTemperature(URL url, int numDays, String measureUnit) {
        Float temperature;
        BigDecimal minTemperature = getJson(url).getJSONArray("DailyForecasts").getJSONObject(numDays).getJSONObject("Temperature").getJSONObject("Minimum").getBigDecimal("Value"); // Getting the min temperature
        BigDecimal maxTemperature = getJson(url).getJSONArray("DailyForecasts").getJSONObject(numDays).getJSONObject("Temperature").getJSONObject("Maximum").getBigDecimal("Value"); // Getting the max temperature
        temperature = (minTemperature.floatValue() + maxTemperature.floatValue()) / 2; // Calculating the average temperature
        if (!measureUnit.equals("3")) { // If the user chose Imperial, the temperature is converted to Farenhait
            temperature = (temperature - 32) / 1.8f;
        }
        return temperature;
    }

}
