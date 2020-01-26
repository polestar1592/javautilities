package jp.util.functional.algebra.integers;

import jp.util.functional.Triple;

// TODO math? utils?
public final class EuclideanAlgorithm {

    private EuclideanAlgorithm() {
    }

    public static long calculateGcd(long a, long b) {
        return solveEquation(a, b).getRight();
    }

    public static long calculateInverse(long x, long p) {
        return solveEquation(x, p).getLeft();
    }

    // solve ax + by = gcd(a, b).
    // returns (x, y, gcd(a, b))
    private static Triple<Long, Long, Long> solveEquation(long a, long b) {
        long s = a;
        long t = b;
        long u = 1;
        long v = 0;
        long w = 0;

        while (s > 0) {
            long q = t / s;
            w = t - q * s;
            t = s;
            s = w;
            w = v - q * u;
            v = u;
            u = w;
        }
        return Triple.of(v, u, Math.abs(w));
    }
}
