/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alvaroweatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 *
 * @author Alvaro
 *
 * Description: This is the main class
 */
public class AlvaroWeatherApp {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, ParseException {

        Validations validate = new Validations();
        DaysOfDailyForecast daysOfDailyForecast = new DaysOfDailyForecast();
        
        String insertedCity;
        String insertedCountryCode;
        String measureUnit;
        String numberDays;

        boolean correctOption = false;

        // Requesting the opption to the user
        System.out.println("Hi,this is the Alvaro Weather App. \nPlease, chose one of the below options: (Write the option number that you want)");
        System.out.println(" 1. Get the current weather of a location \n"
                + " 2. Get the weather forecast for a number of days (max 5 days) \n"
                + " 3. Exit");

        // Enter data 
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Repeating the request while the response is wrong.
        do {
            // Reading data using readLine
            String response = reader.readLine();

            // Checking user option
            if (validate.checkOption(response)) {
                // If the value is right checking what the user chosed 
                switch (response) {
                    case "1":
                        System.out.println("Insert the name of the city, please: ");
                        insertedCity =  reader.readLine();
                        System.out.println("Insert the country code, please: (Example: Spain - ES)");
                        insertedCountryCode =  reader.readLine();
                        System.out.println("Insert the units of measurement, please: "
                                + "\n1. Default"
                                + "\n2. Metric (Celsius)"
                                + "\n3. Imperial (Fahrenheit)");
                        measureUnit =  reader.readLine();
                        System.out.println("-----------RESULT------------");
                        System.out.println(daysOfDailyForecast.getCurrentWeather(insertedCity, insertedCountryCode, measureUnit));
                        break;
                    case "2":
                        System.out.println("chosed 2");
                        break;
                    case "3":
                        System.out.println("chosed 3");
                        break;
                }
                // Go out of the while
                correctOption = true;
            } else {
                // If the value is not right
                System.out.println("Please, chose between 1, 2 or 3.");
            }
        } while (!correctOption);

    }

}
