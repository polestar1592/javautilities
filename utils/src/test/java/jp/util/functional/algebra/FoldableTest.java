package jp.util.functional.algebra;

import jp.util.functional.examples.ListFunctor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FoldableTest {

    @Test
    public void testFoldable() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        ListFunctor<String> testee = ListFunctor.of(list);
        Assert.assertEquals(testee.foldL(String::concat, "0"), "0123");
        Assert.assertEquals(testee.foldR(String::concat, "4"), "1234");

        Assert.assertEquals(
                testee.foldR((elem, l) -> {
                    l.add(elem);
                    return l;
                }, new ArrayList<>()),
                Arrays.asList("3", "2", "1"));

        Assert.assertEquals(
                testee.foldL((l, elem) -> {
                    l.add(elem);
                    return l;
                }, new ArrayList<>()),
                Arrays.asList("1", "2", "3"));
    }
}
