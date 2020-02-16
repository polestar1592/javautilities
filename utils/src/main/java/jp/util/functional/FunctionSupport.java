package jp.util.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionSupport {

    private FunctionSupport() {
    }

    public static <T, U, R> Function<U, R> partial(BiFunction<T, U, R> biFunction, T t) {
        return u -> biFunction.apply(t, u);
    }

    public static <S, T, U, R> BiFunction<T, U, R> partial(TriFunction<S, T, U, R> triFunction, S s) {
        return (t, u) -> triFunction.apply(s, t, u);
    }

    public static <S, T, U, R> Function<U, R> partial(TriFunction<S, T, U, R> triFunction, S s, T t) {
        return u -> triFunction.apply(s, t, u);
    }

    public static <T, U, R> Function<T, Function<U, R>> curried(BiFunction<T, U, R> biFunction) {
        return t -> (u -> biFunction.apply(t, u));
    }

    public static <S, T, U, R> Function<S, Function<T, Function<U, R>>> curried(TriFunction<S, T, U, R> biFunction) {
        return s -> (t -> (u -> biFunction.apply(s, t, u)));
    }
}
