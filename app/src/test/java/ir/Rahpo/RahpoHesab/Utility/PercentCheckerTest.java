package ir.Rahpo.RahpoHesab.Utility;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PercentCheckerTest {

    private final PercentChecker percentChecker = new PercentChecker();

    @Test
    public void testEmptyString() {
        boolean doesMatch = percentChecker.matches("");
        assertTrue(doesMatch);
    }
    @Test
    public void testOneDigitPercent() {
        boolean doesMatch = percentChecker.matches("2");
        assertTrue(doesMatch);
    }
    @Test
    public void testTwoDigitPercent() {
        boolean doesMatch = percentChecker.matches("23");
        assertTrue(doesMatch);
    }
    @Test
    public void testThreeDigitPercent() {
        boolean doesMatch = percentChecker.matches("234");
        assertFalse(doesMatch);
    }
    @Test
    public void testDotAlone() {
        boolean doesMatch = percentChecker.matches(".");
        assertFalse(doesMatch);
    }
    @Test
    public void testOneDigitWithDotPercent() {
        boolean doesMatch = percentChecker.matches("2.");
        assertFalse(doesMatch);
    }
    @Test
    public void testOneDigitOneDecimalPercent() {
        boolean doesMatch = percentChecker.matches("2.5");
        assertTrue(doesMatch);
    }
    @Test
    public void testTwoDigitThreeDecimalPercent() {
        boolean doesMatch = percentChecker.matches("25.321");
        assertTrue(doesMatch);
    }
    @Test
    public void testTwoDigitFourDecimalPercent() {
        boolean doesMatch = percentChecker.matches("25.3217");
        assertFalse(doesMatch);
    }
    @Test
    public void testOneDecimalPercent() {
        boolean doesMatch = percentChecker.matches(".2");
        assertTrue(doesMatch);
    }
    @Test
    public void testThreeDecimalPercent() {
        boolean doesMatch = percentChecker.matches(".245");
        assertTrue(doesMatch);
    }
    @Test
    public void testFourDecimalPercent() {
        boolean doesMatch = percentChecker.matches(".2456");
        assertFalse(doesMatch);
    }
}
