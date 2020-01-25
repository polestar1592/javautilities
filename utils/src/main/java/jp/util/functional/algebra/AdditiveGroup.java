package jp.util.functional.algebra;

public interface AdditiveGroup<T> extends AdditiveMonoid<T> {

    T minus(T x);

    default T subtract(T left, T right) {
        return add(left, minus(right));
    }
}
