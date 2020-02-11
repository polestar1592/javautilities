package jp.util.iteration;


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
}
