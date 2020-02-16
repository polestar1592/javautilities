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
            assertNext(0, it);
            assertNext(1, it);
            assertNext(2, it);
            assertNext(3, it);
            assertNext(4, it);
        }
        {
            Iterator<Integer> it = IterationSupport.count(5).iterator();
            assertNext(5, it);
            assertNext(6, it);
            assertNext(7, it);
            assertNext(8, it);
            assertNext(9, it);
        }
        {
            Iterator<Integer> it = IterationSupport.count(10, 2).iterator();
            assertNext(10, it);
            assertNext(12, it);
            assertNext(14, it);
            assertNext(16, it);
            assertNext(18, it);
        }
    }

    @Test
    public void test_cycle() {
        {
            Iterator<Integer> it = IterationSupport.cycle(Arrays.asList(1, 2, 3)).iterator();
            assertNext(1, it);
            assertNext(2, it);
            assertNext(3, it);
            assertNext(1, it);
            assertNext(2, it);
            assertNext(3, it);
            assertNext(1, it);
            assertNext(2, it);
            assertNext(3, it);
        }
        {
            Iterator<Integer> it = IterationSupport.cycle(EMPTY_INTEGER_LIST).iterator();
            assertNextFailed(it);
        }
    }

    @Test
    public void test_repeat() {
        Iterator<Integer> it = IterationSupport.repeat(1).iterator();
        assertNext(1, it);
        assertNext(1, it);
        assertNext(1, it);
        assertNext(1, it);
        assertNext(1, it);
    }

    @Test
    public void test_zip() {
        List<Integer> ls = Arrays.asList(1, 2, 3, 4, 5);
        List<String> rs = Arrays.asList("hoge", "foo", "bar");
        List<Boolean> ms = Arrays.asList(true, false, false, false, true);
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.zip(ls, rs).iterator();
            assertNext(Pair.of(1, "hoge"), it);
            assertNext(Pair.of(2, "foo"), it);
            assertNext(Pair.of(3, "bar"), it);
            assertNextFailed(it);
        }
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.zip(EMPTY_INTEGER_LIST, rs).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.zip(ls, EMPTY_STRING_LIST).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.zip(ls, ms, rs).iterator();
            assertNext(Triple.of(1, true, "hoge"), it);
            assertNext(Triple.of(2, false, "foo"), it);
            assertNext(Triple.of(3, false, "bar"), it);
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.zip(EMPTY_INTEGER_LIST, ms, rs).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.zip(ls, EMPTY_BOOLEAN_LIST, rs).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.zip(ls, ms, EMPTY_STRING_LIST).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<Pair<Integer, String>> it = IterationSupport.zip(ls.stream(), rs.stream()).iterator();
            assertNext(Pair.of(1, "hoge"), it);
            assertNext(Pair.of(2, "foo"), it);
            assertNext(Pair.of(3, "bar"), it);
            assertNextFailed(it);
        }
        {
            Iterator<Triple<Integer, Boolean, String>> it = IterationSupport.zip(ls.stream(), ms.stream(), rs.stream()).iterator();
            assertNext(Triple.of(1, true, "hoge"), it);
            assertNext(Triple.of(2, false, "foo"), it);
            assertNext(Triple.of(3, false, "bar"), it);
            assertNextFailed(it);
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
            assertNext("1|hoge", it);
            assertNext("2|foo", it);
            assertNext("3|bar", it);
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(EMPTY_INTEGER_LIST, rs,
                    (a, b) -> String.join("|", Objects.toString(a), Objects.toString(b))).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(ls, EMPTY_STRING_LIST,
                    (a, b) -> String.join("|", Objects.toString(a), Objects.toString(b))).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(ls, ms, rs,
                    (a, b, c) -> String.join("|", Objects.toString(a), Objects.toString(b), Objects.toString(c))).iterator();
            assertNext("1|true|hoge", it);
            assertNext("2|false|foo", it);
            assertNext("3|false|bar", it);
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(EMPTY_INTEGER_LIST, ms, rs,
                    (a, b, c) -> String.join("|", Objects.toString(a), Objects.toString(b), Objects.toString(c))).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(ls, EMPTY_BOOLEAN_LIST, rs,
                    (a, b, c) -> String.join("|", Objects.toString(a), Objects.toString(b), Objects.toString(c))).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(ls, ms, EMPTY_STRING_LIST,
                    (a, b, c) -> String.join("|", Objects.toString(a), Objects.toString(b), Objects.toString(c))).iterator();
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(ls.stream(), rs.stream(),
                    (a, b) -> String.join("|", Objects.toString(a), Objects.toString(b))).iterator();
            assertNext("1|hoge", it);
            assertNext("2|foo", it);
            assertNext("3|bar", it);
            assertNextFailed(it);
        }
        {
            Iterator<String> it = IterationSupport.zip(ls.stream(), ms.stream(), rs.stream(),
                    (a, b, c) -> String.join("|", Objects.toString(a), Objects.toString(b), Objects.toString(c))).iterator();
            assertNext("1|true|hoge", it);
            assertNext("2|false|foo", it);
            assertNext("3|false|bar", it);
            assertNextFailed(it);
        }
    }

    @Test
    public void test_unzip2() {
        List<Pair<Integer, String>> pairs = Arrays.asList(
                Pair.of(1, "hoge"),
                Pair.of(2, "foo"),
                Pair.of(3, "bar")
        );
        {
            Pair<Iterable<Integer>, Iterable<String>> unzip2 = IterationSupport.unzip2(pairs);
            Iterator<Integer> lit = unzip2.getLeft().iterator();
            assertNext(1, lit);
            assertNext(2, lit);
            assertNext(3, lit);
            assertNextFailed(lit);
            Iterator<String> rit = unzip2.getRight().iterator();
            assertNext("hoge", rit);
            assertNext("foo", rit);
            assertNext("bar", rit);
            assertNextFailed(rit);
        }
        {
            Pair<Iterable<Integer>, Iterable<String>> unzip = IterationSupport.unzip2(new ArrayList<>());
            Iterator<Integer> lit = unzip.getLeft().iterator();
            assertNextFailed(lit);
            Iterator<String> rit = unzip.getRight().iterator();
            assertNextFailed(rit);
        }
    }

    @Test
    public void test_unzip3() {
        List<Triple<Integer, Boolean, String>> triples = Arrays.asList(
                Triple.of(1, true, "hoge"),
                Triple.of(2, true, "foo"),
                Triple.of(3, false, "bar")
        );
        {
            Triple<Iterable<Integer>, Iterable<Boolean>, Iterable<String>> unzip3 = IterationSupport.unzip3(triples);
            Iterator<Integer> lit = unzip3.getLeft().iterator();
            assertNext(1, lit);
            assertNext(2, lit);
            assertNext(3, lit);
            assertNextFailed(lit);
            Iterator<Boolean> mit = unzip3.getMiddle().iterator();
            assertNext(true, mit);
            assertNext(true, mit);
            assertNext(false, mit);
            assertNextFailed(mit);
            Iterator<String> rit = unzip3.getRight().iterator();
            assertNext("hoge", rit);
            assertNext("foo", rit);
            assertNext("bar", rit);
            assertNextFailed(rit);
        }
        {
            Triple<Iterable<Integer>, Iterable<Boolean>, Iterable<String>> unzip3 = IterationSupport.unzip3(new ArrayList<>());
            Iterator<Integer> lit = unzip3.getLeft().iterator();
            assertNextFailed(lit);
            Iterator<Boolean> mit = unzip3.getMiddle().iterator();
            assertNextFailed(mit);
            Iterator<String> rit = unzip3.getRight().iterator();
            assertNextFailed(rit);
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
