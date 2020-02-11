package jp.util.iteration;


import jp.util.functional.Pair;
import jp.util.functional.Triple;

import java.util.Iterator;

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
        return () -> new Iterator<Pair<L, R>>() {

            Iterator<L> lit = ls.iterator();

            Iterator<R> rit = rs.iterator();

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
        return () -> new Iterator<Triple<L, M, R>>() {

            Iterator<L> lit = ls.iterator();

            Iterator<M> mit = ms.iterator();

            Iterator<R> rit = rs.iterator();

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
