package ir.Rahpo.RahpoHesab;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculationsTest {

    private final Calculations calc = new Calculations();

    @Test
    public void testMinAllowPriceExample1() {
        String result = calc.getMinAllowedPrice("5000", "0.8");
        assertEquals("4,960", result);
    }
    @Test
    public void testMinAllowPriceExample2() {
        String result = calc.getMinAllowedPrice("1000", "0.02");
        assertEquals("999.8", result);
    }
    @Test
    public void testMinAllowPriceExample3() {
        String result = calc.getMinAllowedPrice("1237565", "0.215");
        assertEquals("1,234,904.24", result);
    }
    @Test
    public void testMinAllowPriceExample4() {
        String result = calc.getMinAllowedPrice("50000", "0.05");
        assertEquals("49,975", result);
    }

    @Test
    public void testMaxAllowPriceExample1() {
        String result = calc.getMaxAllowedPrice("50000", "0.05");
        assertEquals("50,025", result);
    }
    @Test
    public void testMaxAllowPriceExample2() {
        String result = calc.getMaxAllowedPrice("1237565", "0.215");
        assertEquals("1,240,225.76", result);
    }
    @Test
    public void testMaxAllowPriceExample3() {
        String result = calc.getMaxAllowedPrice("10250200", "0.075");
        assertEquals("10,257,887.65", result);
    }


    @Test
    public void testFinalBuyCostExample1() {
        String result = calc.getFinalBuyListCost("50000", "2000", "0.08");
        assertEquals("52,040", result);
    }
    @Test
    public void testFinalBuyCostExample2() {
        String result = calc.getFinalBuyListCost("1270235", "3405", "0.125");
        assertEquals("1,275,227.79", result);
    }
    @Test
    public void testFinalBuyCostExample3() {
        String result = calc.getFinalBuyListCost("1533", "125", "0.05");
        assertEquals("1,658.77", result);
    }

    @Test
    public void testNetReceiveExample1() {
        String result = calc.getNetReceive("780000", "0.08", "75000");
        assertEquals("696,813.6", result);
    }
}
