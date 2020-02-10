package jp.util.iteration;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class IterationSupportTest {

    @Test
    public void test_count() {
        {
            List<Integer> actual = IterationSupport.count()
                    .limit(5)
                    .boxed()
                    .collect(Collectors.toList());
            List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4);
            assertEquals(actual, expected);
        }
        {
            List<Integer> actual = IterationSupport.count(5)
                    .limit(5)
                    .boxed()
                    .collect(Collectors.toList());
            List<Integer> expected = Arrays.asList(5, 6, 7, 8, 9);
            assertEquals(actual, expected);
        }
        {
            List<Integer> actual = IterationSupport.count(10, 2)
                    .limit(5)
                    .boxed()
                    .collect(Collectors.toList());
            List<Integer> expected = Arrays.asList(10, 12, 14, 16, 18);
            assertEquals(actual, expected);
        }
    }

    @Test
    public void test_cycle() {
        // TODO
    }

    @Test
    public void test_repeat() {
        List<Integer> actual = IterationSupport.repeat(1)
                .limit(5)
                .collect(Collectors.toList());
        List<Integer> expected = Arrays.asList(1, 1, 1, 1, 1);
        assertEquals(actual, expected);
    }

    @Test
    public void test_accumulate() {
        {
            List<Integer> actual = IterationSupport.accumulate(Arrays.asList(1, 2, 3, 4, 5), Integer::sum)
                    .collect(Collectors.toList());
            List<Integer> expected = Arrays.asList(1, 3, 6, 10, 15);
            assertEquals(actual, expected);
        }
        {
            List<String> actual = IterationSupport.accumulate(Arrays.asList("a", "b", "c", "d", "e"), String::concat)
                    .collect(Collectors.toList());
            List<String> expected = Arrays.asList(
                    "a",
                    "ab",
                    "abc",
                    "abcd",
                    "abcde"
            );
            assertEquals(actual, expected);
        }
    }
}
