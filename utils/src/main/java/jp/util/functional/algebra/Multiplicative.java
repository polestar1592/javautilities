package jp.util.functional.algebra;

public interface Multiplicative<T> {

    T getOne();

    T multiply(T left, T right);

    default T pow(T base, long n) {
        T value = base;
        for (long i = 1; i < n; i++) {
            value = multiply(value, base);
        }
        return value;
    }
}
