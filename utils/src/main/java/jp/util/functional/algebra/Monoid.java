package jp.util.functional.algebra;

public interface Monoid<T> extends Semigroup<T> {

    T getZero();

    default Monoid<T> toDual() {
        Monoid<T> delegate = this;
        return new Monoid<T>() {

            @Override
            public T add(T left, T right) {
                return delegate.add(right, left);
            }

            @Override
            public T getZero() {
                return delegate.getZero();
            }
        };
    }
}