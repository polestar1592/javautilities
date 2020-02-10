package jp.util.iterator;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class IteratorSupportTest {

    @Test
    public void test_count() {
        {
            Iterator<Integer> count = IteratorSupport.count();
            assertEquals(0, (int) count.next());
            assertEquals(1, (int) count.next());
            assertEquals(2, (int) count.next());
            assertEquals(3, (int) count.next());
            assertEquals(4, (int) count.next());
        }
        {
            Iterator<Integer> count = IteratorSupport.count(5);
            assertEquals(5, (int) count.next());
            assertEquals(6, (int) count.next());
            assertEquals(7, (int) count.next());
            assertEquals(8, (int) count.next());
            assertEquals(9, (int) count.next());
        }
        {
            Iterator<Integer> count = IteratorSupport.count(10, 2);
            assertEquals(10, (int) count.next());
            assertEquals(12, (int) count.next());
            assertEquals(14, (int) count.next());
            assertEquals(16, (int) count.next());
            assertEquals(18, (int) count.next());
        }
    }

    @Test
    public void test_cycle() {
        Iterator<Integer> cycle = IteratorSupport.cycle(Arrays.asList(1, 2, 3));
        assertEquals(1, (int) cycle.next());
        assertEquals(2, (int) cycle.next());
        assertEquals(3, (int) cycle.next());
        assertEquals(1, (int) cycle.next());
        assertEquals(2, (int) cycle.next());
        assertEquals(3, (int) cycle.next());
        assertEquals(1, (int) cycle.next());
        assertEquals(2, (int) cycle.next());
        assertEquals(3, (int) cycle.next());
    }

    @Test
    public void test_repeat() {
        Iterator<Integer> repeat = IteratorSupport.repeat(1);
        assertEquals(1, (int) repeat.next());
        assertEquals(1, (int) repeat.next());
        assertEquals(1, (int) repeat.next());
        assertEquals(1, (int) repeat.next());
        assertEquals(1, (int) repeat.next());
    }
}
