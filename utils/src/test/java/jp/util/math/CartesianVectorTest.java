package jp.util.math;

import org.junit.Test;

import static org.junit.Assert.*;

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
    public void test_dot() {
        double actual = V1.dot(V2);
        double expected = (1 * -3) + (-2 * 1) + (3 * -2);
        assertEquals(expected, actual, 0);
    }

    @Test
    public void test_cross() {
        CartesianVector actual = V1.cross(V2);
        CartesianVector expected = CartesianVector.of(
                (-2 * -2) - (3 * 1),
                (3 * -3) - (1 * -2),
                (1 * 1) - (-2 * -3));
        assertEquals(expected, actual);
    }

    @Test
    public void test_is_vertical_to() {
        {
            CartesianVector vectorVerticalToV1 = CartesianVector.of(2, -2, -2);
            boolean isVertical = V1.isVerticalTo(vectorVerticalToV1);
            assertTrue(isVertical);
        }
        {
            boolean isVertical = CartesianVector.ZERO.isVerticalTo(V1);
            assertFalse(isVertical);
        }
        {
            boolean isVertical = V1.isVerticalTo(CartesianVector.ZERO);
            assertFalse(isVertical);
        }
        {
            boolean isVertical = V1.isVerticalTo(V1);
            assertFalse(isVertical);
        }
    }

    @Test
    public void test_is_parallel_to() {
        {
            boolean isVertical = V1.isParallelTo(V1.multiplyBy(1.5));
            assertTrue(isVertical);
        }
        {
            boolean isVertical = V1.isParallelTo(V1.multiplyBy(-1.5));
            assertTrue(isVertical);
        }
        {
            boolean isVertical = V1.isVerticalTo(CartesianVector.ZERO);
            assertFalse(isVertical);
        }
        {
            boolean isVertical = CartesianVector.ZERO.isVerticalTo(V1);
            assertFalse(isVertical);
        }
    }
}
