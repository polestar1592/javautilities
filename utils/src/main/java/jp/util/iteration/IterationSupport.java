package jp.util.iteration;


import jp.util.functional.Pair;
import jp.util.functional.TriFunction;
import jp.util.functional.Triple;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.function.BiFunction;
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


    public static <L, R, T> Iterable<T> zip(Iterable<L> ls, Iterable<R> rs, BiFunction<? super L, ? super R, ? extends T> merger) {
        return () -> zip(ls.iterator(), rs.iterator(), merger);
    }

    // 受け取ったストリームは一旦閉じるので注意。
    public static <L, R> Stream<Pair<L, R>> zip(Stream<L> ls, Stream<R> rs) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(zip(ls.iterator(), rs.iterator()), 0), false);
    }

    // 受け取ったストリームは一旦閉じるので注意。
    public static <L, R, T> Stream<T> zip(Stream<L> ls, Stream<R> rs, BiFunction<? super L, ? super R, ? extends T> merger) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(zip(ls.iterator(), rs.iterator(), merger), 0), false);
    }


    public static <L, R> Iterator<Pair<L, R>> zip(Iterator<L> lit, Iterator<R> rit) {
        return zip(lit, rit, Pair::of);
    }


    public static <L, R, T> Iterator<T> zip(Iterator<L> lit, Iterator<R> rit, BiFunction<? super L, ? super R, ? extends T> merger) {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return lit.hasNext() && rit.hasNext();
            }

            @Override
            public T next() {
                return merger.apply(lit.next(), rit.next());
            }
        };
    }

    public static <L, M, R> Iterable<Triple<L, M, R>> zip(Iterable<L> ls, Iterable<M> ms, Iterable<R> rs) {
        return () -> zip(ls.iterator(), ms.iterator(), rs.iterator());
    }

    public static <L, M, R, T> Iterable<T> zip(Iterable<L> ls, Iterable<M> ms, Iterable<R> rs, TriFunction<L, M, R, T> merger) {
        return () -> zip(ls.iterator(), ms.iterator(), rs.iterator(), merger);
    }

    // 受け取ったストリームは一旦閉じるので注意。
    public static <L, M, R> Stream<Triple<L, M, R>> zip(Stream<L> ls, Stream<M> ms, Stream<R> rs) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(zip(ls.iterator(), ms.iterator(), rs.iterator()), 0), false);
    }

    // 受け取ったストリームは一旦閉じるので注意。
    public static <L, M, R, T> Stream<T> zip(Stream<L> ls, Stream<M> ms, Stream<R> rs, TriFunction<L, M, R, T> merger) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(zip(ls.iterator(), ms.iterator(), rs.iterator(), merger), 0), false);
    }

    public static <L, M, R> Iterator<Triple<L, M, R>> zip(Iterator<L> lit, Iterator<M> mit, Iterator<R> rit) {
        return zip(lit, mit, rit, Triple::of);
    }

    public static <L, M, R, T> Iterator<T> zip(Iterator<L> lit, Iterator<M> mit, Iterator<R> rit, TriFunction<L, M, R, T> merger) {
        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                return lit.hasNext() && mit.hasNext() && rit.hasNext();
            }

            @Override
            public T next() {
                return merger.apply(lit.next(), mit.next(), rit.next());
            }
        };
    }

    public static <L, R> Iterable<Pair<L, R>> product(Iterable<L> ls, Iterable<R> rs) {
        return () -> new Iterator<Pair<L, R>>() {

            Iterator<L> lit;

            Iterator<R> rit;

            L lc;

            {
                if (ls.iterator().hasNext() && rs.iterator().hasNext()) {
                    lit = ls.iterator();
                    rit = rs.iterator();
                    lc = lit.next();
                } else {
                    lit = emptyIterator();
                    rit = emptyIterator();
                }
            }

            @Override
            public boolean hasNext() {
                return lit.hasNext() || rit.hasNext();
            }

            @Override
            public Pair<L, R> next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                if (rit.hasNext()) {
                    return Pair.of(lc, rit.next());
                }
                rit = rs.iterator();

                lc = lit.next();
                return Pair.of(lc, rit.next());
            }
        };
    }

    public static <L, M, R> Iterable<Triple<L, M, R>> product(Iterable<L> ls, Iterable<M> ms, Iterable<R> rs) {
        return () -> new Iterator<Triple<L, M, R>>() {

            Iterator<L> lit;

            Iterator<M> mit;

            Iterator<R> rit;

            L lc;

            M mc;

            {
                if (ls.iterator().hasNext() && ms.iterator().hasNext() && rs.iterator().hasNext()) {
                    lit = ls.iterator();
                    mit = ms.iterator();
                    rit = rs.iterator();
                    lc = lit.next();
                    mc = mit.next();
                } else {
                    lit = emptyIterator();
                    mit = emptyIterator();
                    rit = emptyIterator();
                }
            }

            @Override
            public boolean hasNext() {
                return lit.hasNext() || mit.hasNext() || rit.hasNext();
            }

            @Override
            public Triple<L, M, R> next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                if (rit.hasNext()) {
                    return Triple.of(lc, mc, rit.next());
                }
                rit = rs.iterator();

                if (mit.hasNext()) {
                    mc = mit.next();
                    return Triple.of(lc, mc, rit.next());
                }
                mit = ms.iterator();
                mc = mit.next();

                lc = lit.next();
                return Triple.of(lc, mc, rit.next());
            }
        };
    }

    private static <T> Iterator<T> emptyIterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }
        };
    }
}
