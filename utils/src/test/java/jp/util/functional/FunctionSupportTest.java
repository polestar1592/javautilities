package jp.util.functional;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FunctionSupportTest {

    @Test
    public void test_partial() {
        BiFunction<String, Integer, List<String>> bf = (s, i) ->
                Stream.generate(() -> s).limit(i).collect(Collectors.toList());
        TriFunction<Boolean, String, Integer, List<String>> tf = (b, s, i) -> b ? bf.apply(s, i) : Collections.singletonList(s + i);
        {
            Function<Integer, List<String>> actual = FunctionSupport.partial(bf, "hoge");
            Function<Integer, List<String>> expected = i ->
                    Stream.generate(() -> "hoge").limit(i).collect(Collectors.toList());
            assertFunction(expected, actual, 0);
            assertFunction(expected, actual, 1);
            assertFunction(expected, actual, 2);
        }
        {
            BiFunction<String, Integer, List<String>> actual = FunctionSupport.partial(tf, false);
            BiFunction<String, Integer, List<String>> expected = (s, i) -> Collections.singletonList(s + i);
            assertFunction(expected, actual, "hoge", 0);
            assertFunction(expected, actual, "foo", 1);
            assertFunction(expected, actual, "bar", 2);
        }
        {
            Function<Integer, List<String>> actual = FunctionSupport.partial(tf, true, "hoge");
            Function<Integer, List<String>> expected = i ->
                    Stream.generate(() -> "hoge").limit(i).collect(Collectors.toList());
            assertFunction(expected, actual, 0);
            assertFunction(expected, actual, 1);
            assertFunction(expected, actual, 2);
        }
    }

    @Test
    public void test_curried() {
        BiFunction<String, Integer, List<String>> bf = (s, i) ->
                Stream.generate(() -> s).limit(i).collect(Collectors.toList());
        TriFunction<Boolean, String, Integer, List<String>> tf = (b, s, i) -> b ? bf.apply(s, i) : Collections.singletonList(s + i);
        {
            Function<String, Function<Integer, List<String>>> actual = FunctionSupport.curried(bf);
            assertFunction(bf, actual, "hoge", 0);
            assertFunction(bf, actual, "foo", 1);
            assertFunction(bf, actual, "bar", 2);
        }
        {
            Function<Boolean, Function<String, Function<Integer, List<String>>>> actual = FunctionSupport.curried(tf);
            assertFunction(tf, actual, true, "hoge", 0);
            assertFunction(tf, actual, true, "foo", 1);
            assertFunction(tf, actual, true, "bar", 2);
            assertFunction(tf, actual, false, "hoge", 0);
            assertFunction(tf, actual, false, "foo", 1);
            assertFunction(tf, actual, false, "bar", 2);
        }
    }

    public <T, R> void assertFunction(Function<T, R> expected, Function<T, R> actual, T t) {
        assertEquals(expected.apply(t), actual.apply(t));
    }

    public <T, U, R> void assertFunction(BiFunction<T, U, R> expected, BiFunction<T, U, R> actual, T t, U u) {
        assertEquals(expected.apply(t, u), actual.apply(t, u));
    }

    public <T, U, R> void assertFunction(BiFunction<T, U, R> expected, Function<T, Function<U, R>> actual, T t, U u) {
        assertEquals(expected.apply(t, u), actual.apply(t).apply(u));
    }

    public <S, T, U, R> void assertFunction(TriFunction<S, T, U, R> expected, Function<S, Function<T, Function<U, R>>> actual, S s, T t, U u) {
        assertEquals(expected.apply(s, t, u), actual.apply(s).apply(t).apply(u));
    }
}
