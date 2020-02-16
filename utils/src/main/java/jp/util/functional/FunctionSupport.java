package jp.util.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionSupport {

    private FunctionSupport() {
    }

    public static <T, U, R> Function<U, R> partial(BiFunction<T, U, R> f, T t) {
        return u -> f.apply(t, u);
    }

    public static <S, T, U, R> BiFunction<T, U, R> partial(TriFunction<S, T, U, R> f, S s) {
        return (t, u) -> f.apply(s, t, u);
    }

    public static <S, T, U, R> Function<U, R> partial(TriFunction<S, T, U, R> f, S s, T t) {
        return u -> f.apply(s, t, u);
    }

    public static <T, U, R> Function<T, Function<U, R>> curried(BiFunction<T, U, R> f) {
        return t -> (u -> f.apply(t, u));
    }

    public static <S, T, U, R> Function<S, Function<T, Function<U, R>>> curried(TriFunction<S, T, U, R> f) {
        return s -> (t -> (u -> f.apply(s, t, u)));
    }

    public static <T, U, R> R apply(BiFunction<T, U, R> f, Pair<T, U> pair) {
        return f.apply(pair.getLeft(), pair.getRight());
    }

    public static <S, T, U, R> R apply(TriFunction<S, T, U, R> f, Triple<S, T, U> triple) {
        return f.apply(triple.getLeft(), triple.getMiddle(), triple.getRight());
    }
}
