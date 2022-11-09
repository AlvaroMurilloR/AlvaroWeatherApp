/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alvaroweatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvaro
 *
 * Description: This is the main class
 */
public class AlvaroWeatherApp {

    // Validation class to do some validations with the inserted values of the user.
    static Validations validate = new Validations();

    // Used variables to save the inserted values of the user
    static String insertedCity;
    static String insertedCountryCode;
    static String measureUnit;
    static String numDays;

    // Enter data 
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Class to use the necessary functions to get the weather data.
        DaysOfDailyForecast daysOfDailyForecast = new DaysOfDailyForecast();
        // Boolean to check if the user insert a right value
        boolean correctOption = false;

        // Requesting the opption to the user
        System.out.println("Hi,this is the Alvaro Weather App. \nPlease, chose one of the below options: (Write the option number that you want)");
        System.out.println(" 1. Get the current weather of a location \n"
                + " 2. Get the weather forecast for a number of days (max 5 days) \n"
                + " 3. Exit");

        // The app ask for a option again in case the user inserted a wrong value
        do {
            try {
                // Reading data using readLine
                String response = reader.readLine();

                boolean foundCity = false;
                boolean correctNumDays = false;
                        
                // Checking user option
                if (validate.checkOption(response)) {
                    // If the value is right checking what the user chosed
                    switch (response) {
                        case "1":
                            // If the city is not found, the user must to write the city again
                            do {
                                requestLocationAndMeasureUnit();
                                String result = daysOfDailyForecast.getCurrentWeather(insertedCity, insertedCountryCode, measureUnit);
                                if (!result.equals("City Not Found")) {
                                    foundCity = true;
                                }
                                System.out.println("-----------RESULT------------");
                                System.out.println(result);
                            } while (!foundCity);
                            break;
                        case "2":
                            
                            // If the city is not found, the user must to write the city again
                            do {
                                requestLocationAndMeasureUnit();
                                do{
                                System.out.println("Insert the number of the days that you want to know the forecast (max 5), please: ");
                                numDays = reader.readLine();
                                if(validate.checkNumDays(numDays)){
                                    correctNumDays = true;
                                }else{
                                    System.out.println("\nThe number of the days must be from 1 to 5");
                                }
                                }while(!correctNumDays);
                                String result = daysOfDailyForecast.getWeatherForecastDays(insertedCity, insertedCountryCode, measureUnit, Integer.parseInt(numDays));
                                if (!result.equals("City Not Found")) {
                                    foundCity = true;
                                }
                                System.out.println("-----------RESULT------------");
                                System.out.println(result);
                            } while (!foundCity);
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
            } catch (IOException ex) {
                Logger.getLogger(AlvaroWeatherApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (!correctOption);

    }

    /**
     * This function request to the user the name of the city, the country code
     * and the measure units.
     */
    public static void requestLocationAndMeasureUnit() {
        try {
            boolean measureUnitCorrect = false;
            System.out.println("Insert the name of the city, please: ");
            insertedCity = reader.readLine();
            System.out.println("Insert the country code, please: (Example: Spain - ES)");
            insertedCountryCode = reader.readLine();
            // The app ask for a measure unit again in case the user inserted a wrong value
            do {
                System.out.println("Insert the units of measurement, please: "
                        + "\n1. Default"
                        + "\n2. Metric (Celsius)"
                        + "\n3. Imperial (Fahrenheit)");
                measureUnit = reader.readLine();
                if (validate.checkOption(measureUnit)) {
                    measureUnitCorrect = true;
                } else {
                    System.out.println("\nPlease, chose between 1, 2 or 3.");
                }
            } while (!measureUnitCorrect);
        } catch (IOException ex) {
            Logger.getLogger(AlvaroWeatherApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
