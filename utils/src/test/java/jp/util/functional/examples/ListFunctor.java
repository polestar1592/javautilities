package jp.util.functional.examples;

import jp.util.functional.Functor;
import jp.util.functional.algebra.Foldable;
import jp.util.functional.algebra.Monoid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO 微妙。 applicativeとかmonadになった時、本当に有用なら、delegateとかはやめてもうちょい頑張ってmain下へ移動。
public class ListFunctor<T> implements Functor<ListFunctor<?>, T>, Foldable<T> {

    private List<T> delegate;

    public static <S> ListFunctor<S> of(S object) {
        return new ListFunctor<>(object);
    }

    public static <S> ListFunctor<S> of(List<S> object) {
        return new ListFunctor<>(object);
    }

    private ListFunctor(T object) {
        delegate = Collections.singletonList(object);
    }

    private ListFunctor(List<T> object) {
        delegate = (new ArrayList<>(object));
    }

    @Override
    public <R> ListFunctor<R> map(Function<? super T, ? extends R> function) {
        return new ListFunctor<>(delegate.stream().map(function).collect(Collectors.toList()));
    }

    @Override
    public <M> M foldMap(Function<? super T, ? extends M> mapper, Monoid<M> adder) {
        return delegate.stream().map(mapper)
                .reduce(adder.getZero(), adder::add, adder::add);
    }

    public List<T> toList() {
        return Collections.unmodifiableList(delegate);
    }
}
