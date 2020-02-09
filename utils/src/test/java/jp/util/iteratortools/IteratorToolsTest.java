package jp.util.iteratortools;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class IteratorToolsTest {

    @Test
    public void test_count() {
        {
            List<Integer> actual = IteratorTools.count()
                    .limit(5)
                    .collect(Collectors.toList());
            List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4);
            assertEquals(actual, expected);
        }
        {
            List<Integer> actual = IteratorTools.count(5)
                    .limit(5)
                    .collect(Collectors.toList());
            List<Integer> expected = Arrays.asList(5, 6, 7, 8, 9);
            assertEquals(actual, expected);
        }
        {
            List<Integer> actual = IteratorTools.count(10, 2)
                    .limit(5)
                    .collect(Collectors.toList());
            List<Integer> expected = Arrays.asList(10, 12, 14, 16, 18);
            assertEquals(actual, expected);
        }
    }

    @Test
    public void test_repeat() {
        List<Integer> actual = IteratorTools.repeat(1)
                .limit(5)
                .collect(Collectors.toList());
        List<Integer> expected = Arrays.asList(1, 1, 1, 1, 1);
        assertEquals(actual, expected);
    }
}
