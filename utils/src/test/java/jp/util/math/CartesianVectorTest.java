package jp.util.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CartesianVectorTest {

    CartesianVector V1 = CartesianVector.of(1, -2, 3);

    CartesianVector V2 = CartesianVector.of(-3, 1, -2);

    @Test
    public void test_abs() {
        {
            double actual = V1.abs();
            double expected = Math.sqrt(14);
            assertEquals(expected, actual, 0);
        }
    }

    @Test
    public void test_multiplyBy() {
        {
            CartesianVector actual = V1.multiplyBy(1.5);
            CartesianVector expected = CartesianVector.of(1 * 1.5, -2 * 1.5, 3 * 1.5);
            assertEquals(expected, actual);
        }
        {
            CartesianVector actual = V1.multiplyBy(0);
            CartesianVector expected = CartesianVector.ZERO;
            assertEquals(expected, actual);
        }
        {
            CartesianVector actual = V1.multiplyBy(-1.5);
            CartesianVector expected = CartesianVector.of(1 * -1.5, -2 * -1.5, 3 * -1.5);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void test_add() {
        CartesianVector actual = V1.add(V2);
        CartesianVector expected = CartesianVector.of(1 + -3, -2 + 1, 3 + -2);
        assertEquals(expected, actual);
    }

    @Test
    public void test_subtract() {
        CartesianVector actual = V1.subtract(V2);
        CartesianVector expected = CartesianVector.of(1 - -3, -2 - 1, 3 - -2);
        assertEquals(expected, actual);
    }

    @Test
    public void test_scalar_product() {
        double actual = V1.dot(V2);
        double expected = (1 * -3) + (-2 * 1) + (3 * -2);
        assertEquals(expected, actual, 0);
    }

    @Test
    public void test_vector_product() {
        CartesianVector actual = V1.cross(V2);
        CartesianVector expected = CartesianVector.of(
                (-2 * -2) - (3 * 1),
                (3 * -3) - (1 * -2),
                (1 * 1) - (-2 * -3));
        assertEquals(expected, actual);
    }
}
