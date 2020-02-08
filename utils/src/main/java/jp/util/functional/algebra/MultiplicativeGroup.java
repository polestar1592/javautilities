package jp.util.functional.algebra;

public interface MultiplicativeGroup<T> extends MultiplicativeMonoid<T> {

    T invert(T x);

    default T divide(T left, T right) {
        return multiply(left, invert(right));
    }
}
