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

        BigDecimal a = divideByHundred(sourcePriceBig, tolerancePercentBig);
        BigDecimal addition = sourcePriceBig.add(a);

        return decimalFormat.format(addition);
    }

    public String getFinalBuyListCost(String buyListPrice, String processCost, String commissionPercent) {
        BigDecimal buyListPriceBig = new BigDecimal(buyListPrice);
        BigDecimal processCostBig = new BigDecimal(processCost);
        BigDecimal commissionPercentBig = new BigDecimal(commissionPercent);

        BigDecimal divide = divideByHundred(buyListPriceBig, commissionPercentBig);

        BigDecimal result = buyListPriceBig.add(processCostBig).add(divide);

        return decimalFormat.format(result);
    }


    public String getNetReceive(String sellPrice, String commissionPercent, String processPrice) {
        BigDecimal sellPriceBig = new BigDecimal(sellPrice);
        BigDecimal commissionPercentBig = new BigDecimal(commissionPercent);
        BigDecimal processPriceBig = new BigDecimal(processPrice);

        BigDecimal divided = divideByHundred(sellPriceBig, commissionPercentBig);
        BigDecimal tax = divided.add(processPriceBig).multiply(BigDecimal.TEN).divide(new BigDecimal("100"));

        BigDecimal result = sellPriceBig.subtract(divided).subtract(processPriceBig).subtract(tax);

        return decimalFormat.format(result);
    }

    private BigDecimal divideByHundred(BigDecimal num1, BigDecimal num2) {
        return num1.multiply(num2).divide(new BigDecimal("100"));
    }
}
