package jp.util.functional;

import java.util.function.Function;

public interface FunctionFunctor<S, T> extends Functor<FunctionFunctor<S, ?>, T>, Function<S, T> {

    @Override
    default <R> FunctionFunctor<S, R> map(Function<? super T, ? extends R> function) {
        return this.andThen(function)::apply;
    }
}
