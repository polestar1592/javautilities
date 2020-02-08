package jp.util.functional.algebra;

public interface Group<T> extends Monoid<T> {

    T negate(T element);

    default T subtract(T left, T right) {
        return add(left, negate(right));
    }
}
