package jp.util.functional;

import java.util.function.Function;

public interface Functor<F, T> {

    <R> Functor<F, R> map(Function<? super T, ? extends R> function);
}
