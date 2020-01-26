package jp.util.functional.algebra;

public interface MultiplicativeMonoid<T> extends Semigroup<T> {

    T getOne();

    T multiply(T left, T right);

    default T pow(T base, long n) {
        T value = base;
        for (long i = 1; i < n; i++) {
            value = multiply(value, base);
        }
        return value;
    }

    @Override
    default T operate(T left, T right) {
        return multiply(left, right);
    }
}
