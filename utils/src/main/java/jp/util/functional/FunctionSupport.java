package jp.util.functional;

import java.util.function.*;

public class FunctionSupport {

    private FunctionSupport() {
    }

    public static <T> Function<T, Boolean> fromPredicate(Predicate<? super T> p) {
        return p::test;
    }

    public static <T, U> BiFunction<T, U, Boolean> fromPredicate(BiPredicate<? super T, ? super U> p) {
        return p::test;
    }

    public static <T, U, R> Function<U, R> partial(BiFunction<? super T, ? super U, ? extends R> f, T t) {
        return u -> f.apply(t, u);
    }

    public static <S, T, U, R> BiFunction<T, U, R> partial(TriFunction<? super S, ? super T, ? super U, ? extends R> f, S s) {
        return (t, u) -> f.apply(s, t, u);
    }

    public static <S, T, U, R> Function<U, R> partial(TriFunction<? super S, ? super T, ? super U, ? extends R> f, S s, T t) {
        return u -> f.apply(s, t, u);
    }

    public static <T, U> Predicate<U> partial(BiPredicate<? super T, ? super U> p, T t) {
        return u -> p.test(t, u);
    }

    public static <T, U> Consumer<U> partial(BiConsumer<? super T, ? super U> c, T t) {
        return u -> c.accept(t, u);
    }

    public static <T, U, R> Function<T, Function<U, R>> curry(BiFunction<? super T, ? super U, ? extends R> f) {
        return t -> (u -> f.apply(t, u));
    }

    public static <S, T, U, R> Function<S, Function<T, Function<U, R>>> curry(TriFunction<? super S, ? super T, ? super U, ? extends R> f) {
        return s -> (t -> (u -> f.apply(s, t, u)));
    }

    public static <T, U> Function<T, Predicate<U>> curry(BiPredicate<? super T, ? super U> p) {
        return t -> (u -> p.test(t, u));
    }

    public static <T, U> Function<T, Consumer<U>> curry(BiConsumer<? super T, ? super U> c) {
        return t -> (u -> c.accept(t, u));
    }

    public static <T, U, R> R apply(BiFunction<? super T, ? super U, ? extends R> f, Pair<T, U> pair) {
        return f.apply(pair.getLeft(), pair.getRight());
    }

    public static <S, T, U, R> R apply(TriFunction<? super S, ? super T, ? super U, R> f, Triple<S, T, U> triple) {
        return f.apply(triple.getLeft(), triple.getMiddle(), triple.getRight());
    }

    public static <T, U> boolean test(BiPredicate<? super T, ? super U> p, Pair<T, U> pair) {
        return p.test(pair.getLeft(), pair.getRight());
    }

    public static <T, U> void accept(BiConsumer<? super T, ? super U> c, Pair<T, U> pair) {
        c.accept(pair.getLeft(), pair.getRight());
    }

    public static <T> Consumer<T> conditionalConsumer(Consumer<? super T> c, Predicate<? super T> p) {
        return t -> {
            if (p.test(t)) c.accept(t);
        };
    }

    public static <T, U> BiConsumer<T, U> conditionalBiConsumer(BiConsumer<? super T, ? super U> c, BiPredicate<? super T, ? super U> p) {
        return (t, u) -> {
            if (p.test(t, u)) c.accept(t, u);
        };
    }

    public static <T, R> Function<T, R> conditionFunction(Function<? super T, ? extends R> f, Predicate<? super T> p) {
        return conditionFunction(f, t -> null, p);
    }

    public static <T, R> Function<T, R> conditionFunction(Function<? super T, ? extends R> f, Function<? super T, ? extends R> g, Predicate<? super T> p) {
        return t -> p.test(t) ? f.apply(t) : g.apply(t);
    }

    public static <T, U, R> BiFunction<T, U, R> conditionBiFunction(BiFunction<? super T, ? super U, ? extends R> f, BiPredicate<? super T, ? super U> p) {
        return conditionBiFunction(f, (t, u) -> null, p);
    }

    public static <T, U, R> BiFunction<T, U, R> conditionBiFunction(BiFunction<? super T, ? super U, ? extends R> f, BiFunction<? super T, ? super U, ? extends R> g, BiPredicate<? super T, ? super U> p) {
        return (t, u) -> p.test(t, u) ? f.apply(t, u) : g.apply(t, u);
    }
}
