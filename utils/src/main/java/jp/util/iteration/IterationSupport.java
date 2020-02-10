package jp.util.iteration;


import java.util.Iterator;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class IterationSupport {

    private IterationSupport() {
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

    public static <T> Stream<T> accumulate(Iterable<T> iterable, BinaryOperator<T> accumulator) {
        Stream.Builder<T> builder = Stream.builder();
        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return Stream.empty();
        }

        T t = iterator.next();
        builder.add(t);

        while (iterator.hasNext()) {
            t = accumulator.apply(t, iterator.next());
            builder.add(t);
        }
        return builder.build();
    }
}
