package ir.Rahpo.RahpoHesab.Utility;

import android.content.SharedPreferences;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class CurrencyFormatter {
    private DecimalFormat currencyFormatter;
    public CurrencyFormatter() {
        currencyFormatter = new DecimalFormat("###,###");
    }
    public String format(String price) {
        price = price.replaceAll(",", "");
        String result;
        if(price.isBlank()) {
            result = "";
        } else {
            try {
                result = currencyFormatter.format(Long.parseLong(price));
            } catch (NumberFormatException e) {
                result = null;
            }
        }
        return result;
    }

    public String parse(String formattedPrice) {
        String result;
        if(formattedPrice.isBlank()) {
            result = "";
        } else {
            try {
                result = currencyFormatter.parse(formattedPrice).toString();
            } catch (ParseException e) {
                result = null;
            }
        }
        return result;
    }
}
