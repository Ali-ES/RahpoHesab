package ir.Rahpo.RahpoHesab.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PercentChecker {
    private Pattern percentPattern = Pattern.compile("(\\d{1,2})?(\\.\\d{1,3})?");

    public boolean matches(String percentText) {
        boolean doesMatch;
        if(percentText.isBlank()) {
            doesMatch = true;
        } else {
            Matcher matcher = percentPattern.matcher(percentText);
            doesMatch = matcher.matches();
        }
        return doesMatch;
    }
}
