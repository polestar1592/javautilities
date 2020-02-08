package jp.util.functional.algebra.integers;

import org.junit.Assert;
import org.junit.Test;

public class PrimeGaloisFieldTest {

    private static final PrimeGaloisField FIELD = PrimeGaloisField.ofOrder(65537L);

    @Test
    public void testAddition() {
        Assert.assertEquals(4L, (long) FIELD.add(65539L, 65539L));
        Assert.assertEquals((long) FIELD.getZero(), (long) FIELD.add(-3L, 3L));
        Assert.assertEquals((long) FIELD.getZero(), (long) FIELD.add(15537L, -15537L));
        Assert.assertEquals((long) FIELD.getZero(), (long) FIELD.add(65539L, -2L));
    }

    @Test
    public void testSubtraction() {
        Assert.assertEquals(65535L, (long) FIELD.negate(65539L));
        Assert.assertEquals(65532L, (long) FIELD.negate(5L));
        Assert.assertEquals(5L, (long) FIELD.subtract(15537L, 15532L));
        Assert.assertEquals(65532L, (long) FIELD.subtract(15532L, 15537L));
    }

    @Test
    public void testMultiplication() {
        Assert.assertEquals(53741L, (long) FIELD.multiply(333L, 555L));
        Assert.assertEquals(11796L, (long) FIELD.multiply(-333L, 555L));
        Assert.assertEquals(11796L, (long) FIELD.multiply(333L, -555L));
    }

    @Test
    public void testDivision() {
        Assert.assertEquals((long) FIELD.getOne(), (long) FIELD.multiply(258L, FIELD.invert(258L)));
        Assert.assertEquals(65535L, (long) FIELD.multiply(-516L, FIELD.invert(258L)));
        Assert.assertEquals((long) FIELD.invert(-2L), (long) FIELD.multiply(-258L, FIELD.invert(516L)));

        Assert.assertEquals((long) FIELD.getOne(), (long) FIELD.divide(258L, 258L));
        Assert.assertEquals(65535L, (long) FIELD.divide(-516L, 258L));
        Assert.assertEquals((long) FIELD.invert(-2L), (long) FIELD.divide(258L, -516L));
    }
}
