package jp.util.functional.algebra;

public interface Monoid<T> extends Semigroup<T> {

    T getZero();
}
