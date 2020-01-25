package jp.util.functional.algebra;

public interface Semigroup<T> {

    T operate(T left, T right);
}
