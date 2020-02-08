package jp.util.functional;

import jp.util.functional.examples.ListFunctor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class FunctorTest {

    @Test
    public void testListFunctor() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Assert.assertArrayEquals(
                ListFunctor.of(list)
                        .map(i -> i * 2)
                        .map(String::valueOf)
                        .toList().toArray(),
                new String[]{"2", "4", "6"});
    }

    @Test
    public void testFunctionFunctor() {
        FunctionFunctor<? super Integer, ? extends Integer> function = i -> i * 3;
        Assert.assertEquals(
                function.map(i -> i * 2)
                        .map(String::valueOf)
                        .apply(1),
                "6");
    }
}
