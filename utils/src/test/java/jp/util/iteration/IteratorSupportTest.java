package jp.util.iteration;

import jp.util.functional.Pair;
import jp.util.functional.Triple;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class IteratorSupportTest {

    private static final List<String> EMPTY_STRING_LIST = new ArrayList<>();

    private static final List<Integer> EMPTY_INTEGER_LIST = new ArrayList<>();

    private static final List<Boolean> EMPTY_BOOLEAN_LIST = new ArrayList<>();

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
            assertNext(Pair.of(1, "hoge"), it);
            assertNext(Pair.of(1, "foo"), it);
            assertNext(Pair.of(1, "bar"), it);
            assertNext(Pair.of(2, "hoge"), it);
            assertNext(Pair.of(2, "foo"), it);
            assertNext(Pair.of(2, "bar"), it);
            assertNextFailed(it);
        }
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.product(EMPTY_INTEGER_LIST, rs).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.product(ls, EMPTY_STRING_LIST).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.product(ls, ms, rs).iterator();
            assertNext(Triple.of(1, true, "hoge"), it);
            assertNext(Triple.of(1, true, "foo"), it);
            assertNext(Triple.of(1, true, "bar"), it);
            assertNext(Triple.of(1, false, "hoge"), it);
            assertNext(Triple.of(1, false, "foo"), it);
            assertNext(Triple.of(1, false, "bar"), it);
            assertNext(Triple.of(2, true, "hoge"), it);
            assertNext(Triple.of(2, true, "foo"), it);
            assertNext(Triple.of(2, true, "bar"), it);
            assertNext(Triple.of(2, false, "hoge"), it);
            assertNext(Triple.of(2, false, "foo"), it);
            assertNext(Triple.of(2, false, "bar"), it);
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.product(EMPTY_INTEGER_LIST, ms, rs).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.product(ls, EMPTY_BOOLEAN_LIST, rs).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.product(ls, ms, EMPTY_STRING_LIST).iterator();
            assertNextFailed(it);
        }
    }

    private <T> void assertNext(T expected, Iterator<T> it) {
        assertTrue(it.hasNext());
        assertEquals(expected, it.next());
    }

    private <T> void assertNextFailed(Iterator<T> it) {
        try {
            it.next();
            fail("NoSuchElementException is expected to be raised.");
        } catch (NoSuchElementException e) {
            // test is succeeded.
        }
    }
}
