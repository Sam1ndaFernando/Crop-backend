package lk.ijse.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static Matcher idValidator(String id){
        return Pattern.compile("\\b(?:MEMBER|FIELD|CROP|EQUIPMENT|VEHICLE|LOG|STEQDET)-\\d+\\b").matcher(id);
    }

    public static Matcher emailValidator(String email){
        return Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matcher(email);
    }

}
