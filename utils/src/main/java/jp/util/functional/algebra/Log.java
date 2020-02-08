package jp.util.functional.algebra;

public class Log<T> implements Monoid<T> {

    private final Multiplicative<T> delegate;

    public static <T> Log<T> of(Multiplicative<T> multiplicative) {
        return new Log<>(multiplicative);
    }

    private Log(Multiplicative<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T getZero() {
        return delegate.getOne();
    }

    @Override
    public T add(T left, T right) {
        return delegate.multiply(left, right);
    }
}
