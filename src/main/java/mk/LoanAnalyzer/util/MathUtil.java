package mk.LoanAnalyzer.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Optional;

/**
 *
 *
 * Created by fja on 23.11.2015.
 */
public class MathUtil {

    private static final double EPSILON = 0.0001d;

    public static final BigDecimal HUNDRED = new BigDecimal("100");

    public static final BigDecimal THOUSAND = new BigDecimal("1000");

    private static DecimalFormat decimalFormat;

    static {
        // Amounts are stored in form 200,00 which can be parsed with following setup:
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');

        decimalFormat = new DecimalFormat("#,###.##", symbols);
        decimalFormat.setParseBigDecimal(true);
    }



    private MathUtil() {
        // No instances
    }


    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(float a, float b) {
        return Math.abs(a - b) < EPSILON;
    }


    /**
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == b) return true; // Handles also both null
        if (a == null || b == null) return false; // Just one can be null
        return a.compareTo(b) == 0;
    }


    public static BigDecimal toPercent(BigDecimal a) {
        return HUNDRED.multiply(a);
    }

    public static BigDecimal fromPercent(BigDecimal a) {
        return a.divide(HUNDRED);
    }

    public static String toString(BigDecimal a) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(true);
        return df.format(a);
    }

    public static String toString(BigDecimal a, int fixedFractionDigits) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(fixedFractionDigits);
        df.setMinimumFractionDigits(fixedFractionDigits);
        df.setGroupingUsed(true);
        return df.format(a);
    }

    public static BigDecimal toBigDecimal(String s) {
        if (StringUtil.isEmptyOrNull(s)) {
            return null;
        }
        return new BigDecimal(s);
    }

    public static BigDecimal parseBigDecimal(String s) throws ParseException {
        if (StringUtil.isEmptyOrNull(s)) {
            return null;
        }
        return (BigDecimal) decimalFormat.parse(s);
    }

    public static BigDecimal addNullSafe(BigDecimal a, BigDecimal b) {
        a = (a != null) ? a : BigDecimal.ZERO;
        b = (b != null) ? b : BigDecimal.ZERO;
        return a.add(b);
    }

    public static BigDecimal round(BigDecimal val, int decimalPlaces) {
        return val.setScale(decimalPlaces, RoundingMode.HALF_UP);
    }

    public static BigDecimal roundRating(BigDecimal rating) {
        if(rating == null) {
            return null;
        }
        if(rating.equals(BigDecimal.ZERO)) {
            return null;
        }

        return rating.setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isNegative(BigDecimal val) {
        return val.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isPositive(BigDecimal val) {
        return val.compareTo(BigDecimal.ZERO) > 0;
    }

    public static Long nullSafeToLong(Integer val) {
        return val != null ? val.longValue() : null;
    }

    public static BigDecimal parseBigDecimalFixed(String value, int decimals) {
        if (value == null || value.length() == 0) {
            return null;
        }
        BigDecimal val = new BigDecimal(value);
        BigDecimal divisor = BigDecimal.TEN.pow(decimals);
        return val.divide(divisor);

    }

    public static int toIntOrMaxSafe(Integer val) {
        return Optional.ofNullable(val).orElse(Integer.MAX_VALUE);
    }

    public static int calculateRoundedPercentage(float part, float total) {
        return Math.round(calculatePercentage(part, total));
    }

    public static float calculatePercentage(float part, float total) {
        if(part == 0 || total == 0){
            return 0;
        }

        return (part / total * 100);
    }

    public static long randomLong() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }
}