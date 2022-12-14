/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alvaroweatherapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 *
 * This class have functions to check if the receibed values are right.
 */
public class Validations {

    /**
     * checkOption: This function ckeck if the user insert a correct value when he is selecting the app option.
     * 
     * @param option
     * @return 
     */
    public boolean checkOption(String option) {
        Pattern pat = Pattern.compile("[1-3]");
        Matcher mat = pat.matcher(option);
        return mat.matches();
    }

    /**
     * This function ckeck if the user insert a correct value when he is inserting the number of the days to know the weather
     * 
     * @param option
     * @return 
     */
    public boolean checkNumDays(String option) {
        Pattern pat = Pattern.compile("[1-5]");
        Matcher mat = pat.matcher(option);
        return mat.matches();
    }
    
}
