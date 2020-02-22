package jp.util.functional;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FunctionSupportTest implements FunctionAssertionMixin {

    public static final Predicate<String> PREDICATE = s -> s.startsWith("h");

    public static final BiPredicate<String, Integer> BI_PREDICATE = (s, i) -> s.charAt(i) == 'o';

    public static final BiFunction<String, Integer, List<String>> BI_FUNCTION = (s, i) ->
            Stream.generate(() -> s).limit(i).collect(Collectors.toList());

    public static final TriFunction<Boolean, String, Integer, List<String>> TRI_FUNCTION = (b, s, i) ->
            b ? BI_FUNCTION.apply(s, i) : Collections.singletonList(s + i);

    @Test
    public void test_fromPredicate() {
        {
            Function<String, Boolean> actual = FunctionSupport.fromPredicate(PREDICATE);
            assertFunction(PREDICATE, actual, "hoge");
            assertFunction(PREDICATE, actual, "foo");
        }
        {
            BiFunction<String, Integer, Boolean> actual = FunctionSupport.fromPredicate(BI_PREDICATE);
            assertFunction(BI_PREDICATE, actual, "hoge", 1);
            assertFunction(BI_PREDICATE, actual, "hoge", 2);
            assertFunction(BI_PREDICATE, actual, "foo", 1);
            assertFunction(BI_PREDICATE, actual, "foo", 2);
        }
    }

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
        {
            Predicate<Integer> actual = FunctionSupport.partial(BI_PREDICATE, "hoge");
            Predicate<Integer> expected = i -> "hoge".charAt(i) == 'o';
            assertFunction(expected, actual, 0);
            assertFunction(expected, actual, 1);
        }
    }

    @Test
    public void test_curry() {
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
        {
            Function<String, Predicate<Integer>> actual = FunctionSupport.curry(BI_PREDICATE);
            assertFunction(BI_PREDICATE, actual, "hoge", 1);
            assertFunction(BI_PREDICATE, actual, "hoge", 2);
            assertFunction(BI_PREDICATE, actual, "foo", 1);
            assertFunction(BI_PREDICATE, actual, "foo", 2);
        }
    }

    @Test
    public void test_apply() {
        {
            List<String> actual = FunctionSupport.apply(BI_FUNCTION, Pair.of("hoge", 2));
            assertEquals(BI_FUNCTION.apply("hoge", 2), actual);
        }
        {
            List<String> actual = FunctionSupport.apply(TRI_FUNCTION, Triple.of(false, "foo", 2));
            assertEquals(TRI_FUNCTION.apply(false, "foo", 2), actual);
        }
        {
            boolean actual = FunctionSupport.test(BI_PREDICATE, Pair.of("hoge", 1));
            assertEquals(BI_PREDICATE.test("hoge", 1), actual);
        }
    }
}
