package jp.util.functional.algebra;

public interface Field<T> extends Ring<T> {

    T invert(T element);

    default T divide(T left, T right) {
        return multiply(left, invert(right));
    }
}
