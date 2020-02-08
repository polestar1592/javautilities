package jp.util.functional.algebra;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Foldable<T> {

    <M> M foldMap(Function<? super T, ? extends M> mapper, Monoid<M> adder);

    default <R> R foldR(BiFunction<? super T, ? super R, ? extends R> reducer, R initial) {
        Endo<R> endo = Endo.get();
        return foldMap(t -> r -> reducer.apply(t, r), endo).apply(initial);
    }

    default <R> R foldL(BiFunction<? super R, ? super T, ? extends R> reducer, R initial) {
        Endo<R> endo = Endo.get();
        return foldMap(t -> r -> reducer.apply(r, t), endo.toDual()).apply(initial);
    }
}
