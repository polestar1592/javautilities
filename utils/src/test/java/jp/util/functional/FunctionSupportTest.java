package jp.util.functional;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FunctionSupportTest {

    public static final BiFunction<String, Integer, List<String>> BI_FUNCTION = (s, i) ->
            Stream.generate(() -> s).limit(i).collect(Collectors.toList());

    public static final TriFunction<Boolean, String, Integer, List<String>> TRI_FUNCTION = (b, s, i) ->
            b ? BI_FUNCTION.apply(s, i) : Collections.singletonList(s + i);

    @Test
    public void test_partial() {
        {
            Function<Integer, List<String>> actual = FunctionSupport.partial(BI_FUNCTION, "hoge");
            Function<Integer, List<String>> expected = i ->
                    Stream.generate(() -> "hoge").limit(i).collect(Collectors.toList());
            assertFunction(expected, actual, 0);
            assertFunction(expected, actual, 1);
            assertFunction(expected, actual, 2);
        }
        {
            BiFunction<String, Integer, List<String>> actual = FunctionSupport.partial(TRI_FUNCTION, false);
            BiFunction<String, Integer, List<String>> expected = (s, i) -> Collections.singletonList(s + i);
            assertFunction(expected, actual, "hoge", 0);
            assertFunction(expected, actual, "foo", 1);
            assertFunction(expected, actual, "bar", 2);
        }
        {
            Function<Integer, List<String>> actual = FunctionSupport.partial(TRI_FUNCTION, true, "hoge");
            Function<Integer, List<String>> expected = i ->
                    Stream.generate(() -> "hoge").limit(i).collect(Collectors.toList());
            assertFunction(expected, actual, 0);
            assertFunction(expected, actual, 1);
            assertFunction(expected, actual, 2);
        }
    }

    @Test
    public void test_curried() {
        {
            Function<String, Function<Integer, List<String>>> actual = FunctionSupport.curry(BI_FUNCTION);
            assertFunction(BI_FUNCTION, actual, "hoge", 0);
            assertFunction(BI_FUNCTION, actual, "foo", 1);
            assertFunction(BI_FUNCTION, actual, "bar", 2);
        }
        {
            Function<Boolean, Function<String, Function<Integer, List<String>>>> actual = FunctionSupport.curry(TRI_FUNCTION);
            assertFunction(TRI_FUNCTION, actual, true, "hoge", 0);
            assertFunction(TRI_FUNCTION, actual, true, "foo", 1);
            assertFunction(TRI_FUNCTION, actual, true, "bar", 2);
            assertFunction(TRI_FUNCTION, actual, false, "hoge", 0);
            assertFunction(TRI_FUNCTION, actual, false, "foo", 1);
            assertFunction(TRI_FUNCTION, actual, false, "bar", 2);
        }
    }

    @Test
    public void test_apply() {
        {
            List<String> actual = FunctionSupport.apply(BI_FUNCTION, Pair.of("hoge", 2));
            assertEquals(Arrays.asList("hoge", "hoge"), actual);
        }
        {
            List<String> actual = FunctionSupport.apply(TRI_FUNCTION, Triple.of(false, "foo", 2));
            assertEquals(Collections.singletonList("foo2"), actual);
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
