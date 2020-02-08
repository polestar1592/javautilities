package jp.util.functional.algebra.integers;

import jp.util.functional.algebra.Field;

// TODO math, util?
public class PrimeGaloisField implements Field<Long> {

    private static final long ZERO = 0L;

    private static final long ONE = 1L;

    private final long order;

    public static PrimeGaloisField ofOrder(long order) {
        return new PrimeGaloisField(order);
    }

    private PrimeGaloisField(long primeNumber) {
        this.order = primeNumber;
        // TODO primality check.
    }

    @Override
    public Long getZero() {
        return ZERO;
    }

    @Override
    public Long getOne() {
        return ONE;
    }

    @Override
    public Long add(Long left, Long right) {
        return canonicalize(canonicalize(left) + canonicalize(right));
    }

    @Override
    public Long negate(Long x) {
        return canonicalize(-x);
    }

    @Override
    public Long multiply(Long left, Long right) {
        return canonicalize(canonicalize(left) * canonicalize(right));
    }

    @Override
    public Long invert(Long x) {
        return canonicalize(ExtendedEuclideanAlgorithm.calculateInverse(canonicalize(x), order));
    }

    // TODO override pow

    private Long canonicalize(Long x) {
        return Math.floorMod(x, order);
    }
}
