package jp.util.functional.algebra;

public interface AdditiveMonoid<T> extends Semigroup<T> {

    T getZero();

    T add(T left, T right);

    @Override
    default T operate(T left, T right) {
        return add(left, right);
    }
}
