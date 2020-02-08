package jp.util.functional.algebra;

public interface Ring<T> extends AdditiveGroup<T>, MultiplicativeMonoid<T> {

    @Override
    default T operate(T left, T right) {
        return multiply(left, right);
    }
}
