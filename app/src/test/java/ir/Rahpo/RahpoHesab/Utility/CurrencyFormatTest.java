package ir.Rahpo.RahpoHesab.Utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

public class CurrencyFormatTest {

    private final CurrencyFormatter currencyFormatter = new CurrencyFormatter();

    @Test
    public void testBlankString() {
        String result = currencyFormatter.format("");
        assertEquals("", result);
    }
    @Test
    public void testEmptyString() {
        String result = currencyFormatter.format(" ");
        assertEquals("", result);
    }
    @Test
    public void testOneDigitPrice() {
        String result = currencyFormatter.format("1");
        String expected =  "1";
        assertEquals(expected, result);
    }
    @Test
    public void testThreeDigitPrice() {
        String result = currencyFormatter.format("123");
        String expected = "123";
        assertEquals(expected, result);
    }
    @Test
    public void testFourDigitPrice() {
        String result = currencyFormatter.format("1234");
        String expected = "1,234";
        assertEquals(expected, result);
    }
    @Test
    public void testSixDigitPrice() {
        String result = currencyFormatter.format("123456");
        String expected = "123,456";
        assertEquals(expected, result);
    }
    @Test
    public void testZeroPrice() {
        String result = currencyFormatter.format("0");
        String expected = "0";
        assertEquals(expected, result);
    }
    @Test
    public void testNegOneDigitPrice() {
        String result = currencyFormatter.format("-1");
        String expected = "-1";
        assertEquals(expected, result);
    }
    @Test
    public void testNegFourDigitPrice() {
        String result = currencyFormatter.format("-1234");
        String expected = "-1,234";
        assertEquals(expected, result);

    }
    @Test
    public void testNonNumberCase() {
        String result = currencyFormatter.format("something");
        assertNull(result);
    }
    @Test
    public void testAlreadyFormattedPrice() {
        String result = currencyFormatter.format("1,23456");
        String expected = "123,456";
        assertEquals(expected, result);
    }

    @Test
    public void parseEmptyString() {
        String result = currencyFormatter.parse("");
        assertEquals("", result);
    }
    @Test
    public void parseBlankString() {
        String result = currencyFormatter.parse(" ");
        assertEquals("", result);
    }
    @Test
    public void parseOneDigitNumberString() {
        String result = currencyFormatter.parse("2");
        assertEquals("2", result);
    }
    @Test
    public void parseFourDigitNumberString() {
        String result = currencyFormatter.parse("1,234");
        assertEquals("1234", result);
    }
    @Test
    public void parseWrongFormattedString() {
        String result = currencyFormatter.parse("something");
        assertNull(result);
    }
    @Test
    public void parseNegOneDigitString() {
        String result = currencyFormatter.parse("-2");
        assertEquals("-2", result);
    }
    @Test
    public void parseNegFourDigitString() {
        String result = currencyFormatter.parse("-1,234");
        assertEquals("-1234", result);
    }
}
