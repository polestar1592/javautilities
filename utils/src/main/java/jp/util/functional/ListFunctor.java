package jp.util.functional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO 微妙。 applicativeとかmonadになった時に有用な感じだったら残す。
public class ListFunctor<T> implements Functor<ListFunctor<?>, T> {

    private Stream<T> delegate;

    public static <S> ListFunctor<S> of(S object) {
        return new ListFunctor<>(object);
    }

    public static <S> ListFunctor<S> of(List<S> object) {
        return new ListFunctor<>(object);
    }

    private ListFunctor(T object) {
        delegate = Stream.of(object);
    }

    private ListFunctor(List<T> object) {
        delegate = object.stream();
    }

    private ListFunctor(Stream<T> object) {
        delegate = object;
    }

    @Override
    public <R> ListFunctor<R> map(Function<? super T, ? extends R> function) {
        return new ListFunctor<>(delegate.map(function));
    }

    public List<T> toList() {
        List<T> result = delegate.collect(Collectors.toList());
        this.delegate = result.stream();
        return result;
    }
}
