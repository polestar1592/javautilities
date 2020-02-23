package jp.util.math;

import jp.util.functional.algebra.integers.ExtendedEuclideanAlgorithm;

public class RationalNumber {
    private long numerator;
    private long denominator;

    public RationalNumber(long numerator, long denominator) throws ArithmeticException {
        if (0L == denominator) {
            throw new ArithmeticException("/ by zero");
        }
        int denominatorSign = denominator < 0 ? -1 : 1;
        // a sign of the numerator equals one of the rational number
        this.numerator = denominatorSign * numerator;
        this.denominator = denominatorSign * denominator;
    }

    public RationalNumber add(RationalNumber rationalNumber) {
        long numerator = rationalNumber.getNumerator();
        long denominator = rationalNumber.getDenominator();
        long gcdOfDenominators = ExtendedEuclideanAlgorithm.calculateGcd(this.denominator, denominator);
        return new RationalNumber(numerator * this.denominator / gcdOfDenominators
                + this.numerator * denominator / gcdOfDenominators,
                this.denominator * denominator / gcdOfDenominators).reduce();
    }

    public RationalNumber minus() {
        return new RationalNumber(-1 * numerator, denominator);
    }

    public RationalNumber subtract(RationalNumber rationalNumber) {
        return this.add(rationalNumber.minus());
    }

    public RationalNumber multiply(RationalNumber rationalNumber) {
        return new RationalNumber(numerator * rationalNumber.getNumerator(),
                denominator * rationalNumber.getDenominator()).reduce();
    }

    public RationalNumber invert() {
        return new RationalNumber(denominator, numerator);
    }

    public RationalNumber divide(RationalNumber rationalNumber) {
        return this.multiply(rationalNumber.invert());
    }

    public RationalNumber reduce() {
        long gcd = ExtendedEuclideanAlgorithm.calculateGcd(numerator, denominator);
        return new RationalNumber(numerator / gcd, denominator / gcd);
    }

    public float toFloat() {
        return (float) numerator / (float) denominator;
    }

    public double toDouble() {
        return (double) numerator / (double) denominator;
    }

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    @Override
    public int hashCode() {
        return (int) (numerator + 31 * denominator);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RationalNumber)) {
            return false;
        }
        RationalNumber cast = (RationalNumber) obj;
        return this.numerator == cast.getNumerator()
                && this.denominator == cast.getDenominator();
    }

    @Override
    public String toString() {
        RationalNumber reduced = reduce();
        if (0 == reduced.getNumerator()) {
            return "0";
        }
        if (1 == reduced.getDenominator()) {
            return String.valueOf(reduced.getNumerator());
        }
        return String.format("%d / %d", reduced.getNumerator(), reduced.getDenominator());
    }
}
