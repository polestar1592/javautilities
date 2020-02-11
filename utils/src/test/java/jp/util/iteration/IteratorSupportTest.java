package jp.util.iteration;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class IteratorSupportTest {

    @Test
    public void test_count() {
        {
            Iterator<Integer> it = IterationSupport.count().iterator();
            assertEquals(0, (int) it.next());
            assertEquals(1, (int) it.next());
            assertEquals(2, (int) it.next());
            assertEquals(3, (int) it.next());
            assertEquals(4, (int) it.next());
        }
        {
            Iterator<Integer> it = IterationSupport.count(5).iterator();
            assertEquals(5, (int) it.next());
            assertEquals(6, (int) it.next());
            assertEquals(7, (int) it.next());
            assertEquals(8, (int) it.next());
            assertEquals(9, (int) it.next());
        }
        {
            Iterator<Integer> it = IterationSupport.count(10, 2).iterator();
            assertEquals(10, (int) it.next());
            assertEquals(12, (int) it.next());
            assertEquals(14, (int) it.next());
            assertEquals(16, (int) it.next());
            assertEquals(18, (int) it.next());
        }
    }

    @Test
    public void test_cycle() {
        Iterator<Integer> it = IterationSupport.cycle(Arrays.asList(1, 2, 3)).iterator();
        assertEquals(1, (int) it.next());
        assertEquals(2, (int) it.next());
        assertEquals(3, (int) it.next());
        assertEquals(1, (int) it.next());
        assertEquals(2, (int) it.next());
        assertEquals(3, (int) it.next());
        assertEquals(1, (int) it.next());
        assertEquals(2, (int) it.next());
        assertEquals(3, (int) it.next());
    }

    @Test
    public void test_repeat() {
        Iterator<Integer> it = IterationSupport.repeat(1).iterator();
        assertEquals(1, (int) it.next());
        assertEquals(1, (int) it.next());
        assertEquals(1, (int) it.next());
        assertEquals(1, (int) it.next());
        assertEquals(1, (int) it.next());
    }
}
