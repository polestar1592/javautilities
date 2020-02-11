package jp.util.iteration;


import jp.util.functional.Pair;
import jp.util.functional.Triple;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterationSupport {

    private IterationSupport() {
    }

    public static Iterable<Integer> count() {
        return count(0);
    }

    public static Iterable<Integer> count(int start) {
        return count(start, 1);
    }

    public static Iterable<Integer> count(int start, int step) {
        return () -> new Iterator<Integer>() {

            private int i = start;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                int j = i;
                i += step;
                return j;
            }
        };
    }

    public static <T> Iterable<T> cycle(Iterable<T> iterable) {
        return () -> new Iterator<T>() {

            private Iterator<T> it = iterable.iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if (it.hasNext()) {
                    return it.next();
                }
                it = iterable.iterator();
                return next();
            }
        };
    }

    public static <T> Iterable<T> repeat(T element) {
        return () -> new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return element;
            }
        };
    }

    public static <L, R> Iterable<Pair<L, R>> zip(Iterable<L> ls, Iterable<R> rs) {
        return () -> zip(ls.iterator(), rs.iterator());
    }

    // 受け取ったストリームは一旦閉じるので注意。
    public static <L, R> Stream<Pair<L, R>> zip(Stream<L> ls, Stream<R> rs) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(zip(ls.iterator(), rs.iterator()), 0), false);
    }

    public static <L, R> Iterator<Pair<L, R>> zip(Iterator<L> lit, Iterator<R> rit) {
        return new Iterator<Pair<L, R>>() {
            @Override
            public boolean hasNext() {
                return lit.hasNext() && rit.hasNext();
            }

            @Override
            public Pair<L, R> next() {
                return Pair.of(lit.next(), rit.next());
            }
        };
    }

    public static <L, M, R> Iterable<Triple<L, M, R>> zip(Iterable<L> ls, Iterable<M> ms, Iterable<R> rs) {
        return () -> zip(ls.iterator(), ms.iterator(), rs.iterator());
    }

    // 受け取ったストリームは一旦閉じるので注意。
    public static <L, M, R> Stream<Triple<L, M, R>> zip(Stream<L> ls, Stream<M> ms, Stream<R> rs) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(zip(ls.iterator(), ms.iterator(), rs.iterator()), 0), false);
    }

    public static <L, M, R> Iterator<Triple<L, M, R>> zip(Iterator<L> lit, Iterator<M> mit, Iterator<R> rit) {
        return new Iterator<Triple<L, M, R>>() {

            @Override
            public boolean hasNext() {
                return lit.hasNext() && mit.hasNext() && rit.hasNext();
            }

            @Override
            public Triple<L, M, R> next() {
                return Triple.of(lit.next(), mit.next(), rit.next());
            }
        };
    }
}
