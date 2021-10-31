package com.paysky.momogrow.utilis;

public class Validation {


    // if you don't care why it fails and only want to know if valid or not
    public static boolean validiate(String pass) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=?-]).{8,40}$";
        return pass.matches(pattern);
    }

    // if you want to know which requirement was not met
    public static boolean validatePasswordLength(String pass) {
        return pass.length() >= 8;
    }

    public static boolean validatePasswordUpperChar(String pass) {
        return pass.matches(".*[A-Z].*");
    }

    public static boolean validatePasswordLowerChar(String pass) {
        return pass.matches(".*[a-z].*");
    }

    public static boolean validatePasswordSpecialChar(String pass) {
        return pass.matches(".*[!@#$%^&*+=?-].*");
    }

}