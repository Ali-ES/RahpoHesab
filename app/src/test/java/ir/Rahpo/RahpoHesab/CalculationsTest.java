package ir.Rahpo.RahpoHesab;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculationsTest {

    private final Calculations calc = new Calculations();

    @Test
    public void testMinAllowPriceExample1() {
        String result = calc.getMinAllowedPrice("5000", "80");
        assertEquals("1,000", result);
    }
    @Test
    public void testMinAllowPriceExample2() {
        String result = calc.getMinAllowedPrice("1000", "2");
        assertEquals("980", result);
    }
    @Test
    public void testMinAllowPriceExample3() {
        String result = calc.getMinAllowedPrice("1237565", "21.5");
        assertEquals("971,488.52", result);
    }
    @Test
    public void testMinAllowPriceExample4() {
        String result = calc.getMinAllowedPrice("50000", "5");
        assertEquals("47,500", result);
    }

    @Test
    public void testMaxAllowPriceExample1() {
        String result = calc.getMaxAllowedPrice("50000", "5");
        assertEquals("52,500", result);
    }
    @Test
    public void testMaxAllowPriceExample2() {
        String result = calc.getMaxAllowedPrice("1237565", "21.5");
        assertEquals("1,503,641.48", result);
    }
    @Test
    public void testMaxAllowPriceExample3() {
        String result = calc.getMaxAllowedPrice("10250200", "7.5");
        assertEquals("11,018,965", result);
    }


    @Test
    public void testFinalBuyCostExample1() {
        String result = calc.getFinalBuyListCost("50000", "2000", "8");
        assertEquals("56,600", result);
    }
    @Test
    public void testFinalBuyCostExample2() {
        String result = calc.getFinalBuyListCost("1270235", "3405", "12.5");
        assertEquals("1,448,637.81", result);
    }
    @Test
    public void testFinalBuyCostExample3() {
        String result = calc.getFinalBuyListCost("1533", "125", "5");
        assertEquals("1,754.82", result);
    }

    @Test
    public void testNetReceiveExample1() {
        String result = calc.getNetReceive("780000", "8", "75000");
        assertEquals("628,860", result);
    }
}
