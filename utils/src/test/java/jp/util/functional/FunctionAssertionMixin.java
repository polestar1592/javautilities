package jp.util.functional;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

interface FunctionAssertionMixin {

    default <T> void assertFunction(Predicate<T> expected, Function<T, Boolean> actual, T t) {
        assertEquals(expected.test(t), actual.apply(t));
    }

    default <T, U> void assertFunction(BiPredicate<T, U> expected, BiFunction<T, U, Boolean> actual, T t, U u) {
        assertEquals(expected.test(t, u), actual.apply(t, u));
    }

    default <T, R> void assertFunction(Function<T, R> expected, Function<T, R> actual, T t) {
        assertEquals(expected.apply(t), actual.apply(t));
    }

    default <T> void assertFunction(Predicate<T> expected, Predicate<T> actual, T t) {
        assertEquals(expected.test(t), actual.test(t));
    }

    default <T, U, R> void assertFunction(BiFunction<T, U, R> expected, BiFunction<T, U, R> actual, T t, U u) {
        assertEquals(expected.apply(t, u), actual.apply(t, u));
    }

    default <T, U, R> void assertFunction(BiFunction<T, U, R> expected, Function<T, Function<U, R>> actual, T t, U u) {
        assertEquals(expected.apply(t, u), actual.apply(t).apply(u));
    }

    default <T, U> void assertFunction(BiPredicate<T, U> expected, Function<T, Predicate<U>> actual, T t, U u) {
        assertEquals(expected.test(t, u), actual.apply(t).test(u));
    }

    default <S, T, U, R> void assertFunction(TriFunction<S, T, U, R> expected, Function<S, Function<T, Function<U, R>>> actual, S s, T t, U u) {
        assertEquals(expected.apply(s, t, u), actual.apply(s).apply(t).apply(u));
    }
}
