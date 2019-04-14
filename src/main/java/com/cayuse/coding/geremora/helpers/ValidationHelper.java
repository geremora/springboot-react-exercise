package com.cayuse.coding.geremora.helpers;

import java.util.regex.Pattern;

public class ValidationHelper {

    public static Boolean validateZipCode(String zipCode){
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(zipCode).matches();
    }
}
