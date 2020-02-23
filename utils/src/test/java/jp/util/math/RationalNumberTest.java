package jp.util.math;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class RationalNumberTest {
    @RunWith(Theories.class)
    public static class ParameterizedTestAdd {
        @DataPoints
        public static Fixture[] parametersForAdd = {
                new Fixture(
                        1, 5,
                        2, 5,
                        3, 5),
                new Fixture(
                        1, 2,
                        1, 3,
                        5, 6
                ),
                new Fixture(
                        1, 5,
                        -2, 5,
                        -1, 5
                ),
                new Fixture(
                        1, 5,
                        2, -5,
                        -1, 5
                ),
                new Fixture(
                        1, 4,
                        1, 4,
                        1, 2
                ),
                new Fixture(
                        1, 5,
                        0, 1,
                        1, 5
                )
        };

        @Theory
        public void testAdd(Fixture p) {
            RationalNumber actual = p.leftRationalNumber.add(p.rightRationalNumber);
            assertThat(actual.getNumerator(), is(p.expectedNumerator));
            assertThat(actual.getDenominator(), is(p.expectedDenominator));
        }
    }

    @RunWith(Theories.class)
    public static class ParameterizedTestSubtract {
        @DataPoints
        public static Fixture[] parametersForSubtract = {
                new Fixture(
                        1, 5,
                        2, 5,
                        -1, 5),
                new Fixture(
                        1, 5,
                        -2, 5,
                        3, 5
                ),
                new Fixture(
                        1, 5,
                        2, -5,
                        3, 5
                ),
                new Fixture(
                        1, 3,
                        1, 2,
                        -1, 6
                ),
                new Fixture(
                        0, 1,
                        2, 5,
                        -2, 5
                )
        };

        @Theory
        public void testSubtract(Fixture p) {
            RationalNumber actual = p.leftRationalNumber.subtract(p.rightRationalNumber);
            assertThat(actual.getNumerator(), is(p.expectedNumerator));
            assertThat(actual.getDenominator(), is(p.expectedDenominator));
        }
    }

    @RunWith(Theories.class)
    public static class ParameterizedTestMultiply {
        @DataPoints
        public static Fixture[] parametersForMultiply = {
                new Fixture(
                        1, 2,
                        1, 5,
                        1, 10),
                new Fixture(
                        -1, 2,
                        1, 3,
                        -1, 6),
                new Fixture(
                        1, -2,
                        -1, 3,
                        1, 6),
                new Fixture(
                        1, -2,
                        0, 1,
                        0, 1)
        };

        @Theory
        public void testMultiply(Fixture p) {
            RationalNumber actual = p.leftRationalNumber.multiply(p.rightRationalNumber);
            assertThat(actual.getNumerator(), is(p.expectedNumerator));
            assertThat(actual.getDenominator(), is(p.expectedDenominator));
        }
    }

    @RunWith(Theories.class)
    public static class ParameterizedTestDivide {
        @DataPoints
        public static Fixture[] parametersForDivide = {
                new Fixture(
                        1, 2,
                        1, 5,
                        5, 2),
                new Fixture(
                        1, 4,
                        3, 2,
                        1, 6),
                new Fixture(
                        1, -2,
                        -1, 3,
                        3, 2),
                new Fixture(
                        0, 1,
                        1, 2,
                        0, 1)
        };

        @Theory
        public void testDivide(Fixture p) {
            RationalNumber actual = p.leftRationalNumber.divide(p.rightRationalNumber);
            assertThat(actual.getNumerator(), is(p.expectedNumerator));
            assertThat(actual.getDenominator(), is(p.expectedDenominator));
        }
    }

    public static class TestZero {
        @Test(expected = ArithmeticException.class)
        public void testConstructor() {
            new RationalNumber(1, 0);
        }

        @Test(expected = ArithmeticException.class)
        public void testInvertZero() {
            RationalNumber zero = new RationalNumber(0, 1);
            zero.invert();
        }
    }

    static class Fixture {
        RationalNumber leftRationalNumber;
        RationalNumber rightRationalNumber;
        long expectedNumerator;
        long expectedDenominator;

        Fixture(long leftNumerator, long leftDenominator,
                long rightNumerator, long rightDenominator,
                long expectedNumerator, long expectedDenominator) {
            this.leftRationalNumber = new RationalNumber(leftNumerator, leftDenominator);
            this.rightRationalNumber = new RationalNumber(rightNumerator, rightDenominator);
            this.expectedNumerator = expectedNumerator;
            this.expectedDenominator = expectedDenominator;
        }
    }
}