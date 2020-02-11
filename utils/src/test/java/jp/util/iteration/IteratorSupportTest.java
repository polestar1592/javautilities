package jp.util.iteration;

import jp.util.functional.Pair;
import jp.util.functional.Triple;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

    @Test
    public void test_zip() {
        List<Integer> ls = Arrays.asList(1, 2, 3, 4, 5);
        List<String> rs = Arrays.asList("hoge", "foo", "bar");
        List<Boolean> ms = Arrays.asList(true, false, false, false, true);
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.zip(ls, rs).iterator();
            assertEquals(Pair.of(1, "hoge"), it.next());
            assertEquals(Pair.of(2, "foo"), it.next());
            assertEquals(Pair.of(3, "bar"), it.next());
            assertFalse(it.hasNext());
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.zip(ls, ms, rs).iterator();
            assertEquals(Triple.of(1, true, "hoge"), it.next());
            assertEquals(Triple.of(2, false, "foo"), it.next());
            assertEquals(Triple.of(3, false, "bar"), it.next());
            assertFalse(it.hasNext());
        }
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.zip(ls.stream(), rs.stream()).iterator();
            assertEquals(Pair.of(1, "hoge"), it.next());
            assertEquals(Pair.of(2, "foo"), it.next());
            assertEquals(Pair.of(3, "bar"), it.next());
            assertFalse(it.hasNext());
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.zip(ls.stream(), ms.stream(), rs.stream()).iterator();
            assertEquals(Triple.of(1, true, "hoge"), it.next());
            assertEquals(Triple.of(2, false, "foo"), it.next());
            assertEquals(Triple.of(3, false, "bar"), it.next());
            assertFalse(it.hasNext());
        }
    }

    @Test
    public void test_zipWIthMerger() {
        List<Integer> ls = Arrays.asList(1, 2, 3, 4, 5);
        List<String> rs = Arrays.asList("hoge", "foo", "bar");
        List<Boolean> ms = Arrays.asList(true, false, false, false, true);
        {
            Iterator<String> it = IterationSupport.zip(ls, rs,
                    (a, b) -> String.join("|", Objects.toString(a), Objects.toString(b))).iterator();
            assertEquals("1|hoge", it.next());
            assertEquals("2|foo", it.next());
            assertEquals("3|bar", it.next());
            assertFalse(it.hasNext());
        }
        {
            Iterator<String> it = IterationSupport.zip(ls, ms, rs,
                    (a, b, c) -> String.join("|", Objects.toString(a), Objects.toString(b), Objects.toString(c))).iterator();
            assertEquals("1|true|hoge", it.next());
            assertEquals("2|false|foo", it.next());
            assertEquals("3|false|bar", it.next());
            assertFalse(it.hasNext());
        }
        {
            Iterator<String> it = IterationSupport.zip(ls.stream(), rs.stream(),
                    (a, b) -> String.join("|", Objects.toString(a), Objects.toString(b))).iterator();
            assertEquals("1|hoge", it.next());
            assertEquals("2|foo", it.next());
            assertEquals("3|bar", it.next());
            assertFalse(it.hasNext());
        }
        {
            Iterator<String> it = IterationSupport.zip(ls.stream(), ms.stream(), rs.stream(),
                    (a, b, c) -> String.join("|", Objects.toString(a), Objects.toString(b), Objects.toString(c))).iterator();
            assertEquals("1|true|hoge", it.next());
            assertEquals("2|false|foo", it.next());
            assertEquals("3|false|bar", it.next());
            assertFalse(it.hasNext());
        }
    }

    @Test
    public void test_product() {
        List<Integer> ls = Arrays.asList(1, 2);
        List<String> rs = Arrays.asList("hoge", "foo", "bar");
        List<Boolean> ms = Arrays.asList(true, false);
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.product(ls, rs).iterator();
            assertEquals(Pair.of(1, "hoge"), it.next());
            assertEquals(Pair.of(1, "foo"), it.next());
            assertEquals(Pair.of(1, "bar"), it.next());
            assertEquals(Pair.of(2, "hoge"), it.next());
            assertEquals(Pair.of(2, "foo"), it.next());
            assertEquals(Pair.of(2, "bar"), it.next());
            assertFalse(it.hasNext());
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.product(ls, ms, rs).iterator();
            assertEquals(Triple.of(1, true, "hoge"), it.next());
            assertEquals(Triple.of(1, true, "foo"), it.next());
            assertEquals(Triple.of(1, true, "bar"), it.next());
            assertEquals(Triple.of(1, false, "hoge"), it.next());
            assertEquals(Triple.of(1, false, "foo"), it.next());
            assertEquals(Triple.of(1, false, "bar"), it.next());
            assertEquals(Triple.of(2, true, "hoge"), it.next());
            assertEquals(Triple.of(2, true, "foo"), it.next());
            assertEquals(Triple.of(2, true, "bar"), it.next());
            assertEquals(Triple.of(2, false, "hoge"), it.next());
            assertEquals(Triple.of(2, false, "foo"), it.next());
            assertEquals(Triple.of(2, false, "bar"), it.next());
            assertFalse(it.hasNext());
        }
    }
}
