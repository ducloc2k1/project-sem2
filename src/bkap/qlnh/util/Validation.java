/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ntan2
 */
public class Validation {
    public static boolean CheckNumeric(String value){
        Pattern patt = Pattern.compile("\\d+");
        Matcher m = patt.matcher(value);
        if(!m.find()){
            return false;
            
        }
        return true;
    }
    public static boolean CheckEmail(String value){
        Pattern patt = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = patt.matcher(value);
        if(!m.find()){
            return false;
        }
        return true;
    }
    public static boolean CheckPhone(String value){
        Pattern patt = Pattern.compile("^((09|03|07|08|05)+([0-9]{8}))$");
        Matcher m = patt.matcher(value);
        if(!m.find()){
            return false;
        }
        return true;
    }
}
