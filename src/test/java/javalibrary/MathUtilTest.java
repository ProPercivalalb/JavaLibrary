package javalibrary;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import javalibrary.math.MathUtil;

public class MathUtilTest {

    @Test
    public void testFactorial() {
        
        Assert.assertEquals(BigInteger.valueOf(10), MathUtil.factorialBound(BigInteger.valueOf(10), BigInteger.valueOf(10)));
        Assert.assertEquals(BigInteger.valueOf(1320), MathUtil.factorialBound(BigInteger.valueOf(12), BigInteger.valueOf(10)));
        Assert.assertEquals(BigInteger.valueOf(0), MathUtil.factorialBound(BigInteger.valueOf(9), BigInteger.valueOf(0)));
        Assert.assertEquals(BigInteger.valueOf(362880), MathUtil.factorialBound(BigInteger.valueOf(9), BigInteger.valueOf(1)));
        
        Assert.assertEquals(BigInteger.valueOf(1), MathUtil.factorialBig(BigInteger.valueOf(0)));
        Assert.assertEquals(BigInteger.valueOf(1), MathUtil.factorialBig(BigInteger.valueOf(1)));
        
        Assert.assertEquals(BigInteger.valueOf(10), MathUtil.choose(5, 2));
        Assert.assertEquals(BigInteger.ONE, MathUtil.choose(14, 14));
        Assert.assertEquals(BigInteger.valueOf(924), MathUtil.choose(12, 6));
        Assert.assertEquals(BigInteger.valueOf(7), MathUtil.choose(7, 6));
        Assert.assertEquals(BigInteger.valueOf(1), MathUtil.choose(7, 0));
        Assert.assertEquals(new BigInteger("1381573867788260067953414430905"), MathUtil.choose(4682, 10));
        
        Assert.assertEquals(BigInteger.valueOf(60), MathUtil.factorialLength(BigInteger.valueOf(5), BigInteger.valueOf(3)));
        Assert.assertEquals(BigInteger.valueOf(30240), MathUtil.factorialLength(BigInteger.valueOf(10), BigInteger.valueOf(5)));
        
        Assert.assertEquals(BigInteger.valueOf(1275), MathUtil.sumNaturalNumbers(BigInteger.valueOf(50)));
        Assert.assertEquals(BigInteger.valueOf(75811141), MathUtil.sumNaturalNumbers(BigInteger.valueOf(12313)));
        
        Assert.assertEquals(BigInteger.valueOf(55), MathUtil.sumSquareNumbers(BigInteger.valueOf(5)));
        Assert.assertEquals(BigInteger.valueOf(6201), MathUtil.sumSquareNumbers(BigInteger.valueOf(26)));
        
        
        
        Assert.assertTrue(MathUtil.allDivisibleBy(new Integer[] { 12, 6, 4, 10 }, 0, 4, 2));
        Assert.assertFalse(MathUtil.allDivisibleBy(new Integer[] { 12, 6, 4, 7 }, 0, 4, 2));
        Assert.assertTrue(MathUtil.allDivisibleBy(new Integer[] { 12, 6, 4, 7 }, 0, 3, 2));
        Assert.assertTrue(MathUtil.allDivisibleBy(new Integer[] { 12, 6, 4, 8 }, 0, 4, 2, 3));
        Assert.assertTrue(MathUtil.allDivisibleBy(new Integer[] { 12, 6, 4, 7 }, 0, 2, 3, 2));
        Assert.assertTrue(MathUtil.allDivisibleBy(new Integer[] { }, 0, 0, 2, 3));
    }
}
