package jp.util.functional.algebra;

public class Exp<T> implements Multiplicative<T> {

    private final Monoid<T> delegate;

    public static <T> Exp<T> of(Monoid<T> monoid) {
        return new Exp<>(monoid);
    }

    private Exp(Monoid<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T getOne() {
        return delegate.getZero();
    }

    @Override
    public T multiply(T left, T right) {
        return delegate.add(left, right);
    }
}
