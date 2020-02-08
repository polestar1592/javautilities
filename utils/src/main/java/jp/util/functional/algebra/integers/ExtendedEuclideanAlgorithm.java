package jp.util.functional.algebra.integers;

import jp.util.functional.Triple;

// TODO math? utils?
public final class ExtendedEuclideanAlgorithm {

    private ExtendedEuclideanAlgorithm() {
    }

    public static long calculateGcd(long a, long b) {
        return solve(a, b).getRight();
    }

    public static long calculateInverse(long x, long prime) {
        return solve(x, prime).getLeft();
    }

    // solve ax + by = gcd(a, b).
    // returns (x, y, gcd(a, b))
    public static Triple<Long, Long, Long> solve(long a, long b) {
        // TODO 戻り値 tripleはちょっとキモイ。 それ用の型を作っても良い。
        int signA = a < 0 ? -1 : 1;
        int signB = b < 0 ? -1 : 1;

        long s = signA * a;
        long t = signB * b;
        long v0 = 0, v1 = 1;
        long u0 = 1, u1 = 0;
        while (s > 0) {
            long q = t / s;
            // calculate gcd
            long w = t % s;
            t = s;
            s = w;

            // calculate v0, u0, v1, u1
            // s.t. v0*a + u0*b = t && v1*a + u1*b = s
            w = v0 - q * v1;
            v0 = v1;
            v1 = w;

            w = u0 - q * u1;
            u0 = u1;
            u1 = w;
        }
        return Triple.of(signA * v0, signB * u0, t);
    }
}
