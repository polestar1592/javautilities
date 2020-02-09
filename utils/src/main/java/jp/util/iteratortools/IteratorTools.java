package jp.util.iteratortools;


import java.util.stream.Stream;

public class IteratorTools {

    private IteratorTools() {
    }

    public static Stream<Integer> count() {
        return count(0);
    }

    public static Stream<Integer> count(int start) {
        return count(start, 1);
    }

    public static Stream<Integer> count(int start, int step) {
        return Stream.iterate(start, i -> i + step);
    }

    public static <T> Stream<T> cycle(Iterable<T> iterable) {
        // TODO
        throw new UnsupportedOperationException("this method has not implemented yet.");
    }

    public static <T> Stream<T> repeat(T element) {
        return Stream.generate(() -> element);
    }
}
