package ir.Rahpo.RahpoHesab;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Calculations {
    private final DecimalFormat decimalFormat = new DecimalFormat("###" + Constants.DEFAULT_PRICE_SEPARATOR + "###.##");
    public String getMinAllowedPrice(String sourcePrice, String tolerancePercent) {
        BigDecimal sourcePriceBig = new BigDecimal(sourcePrice);
        BigDecimal tolerancePercentBig = new BigDecimal(tolerancePercent);

        BigDecimal a = sourcePriceBig.multiply(tolerancePercentBig).divide(new BigDecimal("100"));
        BigDecimal subtract = sourcePriceBig.subtract(a);

        return decimalFormat.format(subtract);
    }

    public String getMaxAllowedPrice(String sourcePrice, String tolerancePercent) {
        BigDecimal sourcePriceBig = new BigDecimal(sourcePrice);
        BigDecimal tolerancePercentBig = new BigDecimal(tolerancePercent);

        BigDecimal a = sourcePriceBig.multiply(tolerancePercentBig).divide(new BigDecimal("100"));
        BigDecimal addition = sourcePriceBig.add(a);

        return decimalFormat.format(addition);
    }
}
