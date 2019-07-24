package com.huawei.rtc.utils;

public class Validation {
    public static Boolean IsPhoneNumber(String num) {
        //validate phone numbers of format "1234567890"
        if (num.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(num.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(num.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(num.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;
    }

    //TODO
}
