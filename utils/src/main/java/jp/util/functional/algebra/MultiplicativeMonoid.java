package jp.util.functional.algebra;

public interface MultiplicativeMonoid<T> extends Semigroup<T> {

    T getOne();

    T multiply(T left, T right);

    default T pow(T left, int n) {
        T value = left;
        for (int i = 1; i < n; i++) {
            value = multiply(value, left);
        }
        return value;
    }

    @Override
    default T operate(T left, T right) {
        return multiply(left, right);
    }
}
