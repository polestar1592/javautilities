package jp.util.functional.algebra;

import java.util.function.UnaryOperator;

public final class Endo<T> implements Monoid<UnaryOperator<T>> {

    private static final Endo<Object> INSTANCE = new Endo<>();

    @SuppressWarnings("unchecked")
    public static <T> Endo<T> get() {
        return (Endo<T>) INSTANCE;
    }

    private Endo() {
    }

    @Override
    public UnaryOperator<T> getZero() {
        return UnaryOperator.identity();
    }

    // TODO foldR, foldL されて andThenによる結合が大量に行われたとき、applyされる瞬間コールスタック埋まらない?
    @Override
    public UnaryOperator<T> add(UnaryOperator<T> left, UnaryOperator<T> right) {
        return right.andThen(left)::apply;
    }
}
