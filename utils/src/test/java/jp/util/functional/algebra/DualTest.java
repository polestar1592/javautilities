package jp.util.functional.algebra;

import org.junit.Assert;
import org.junit.Test;

public class DualTest {

    private static final class TestMonoid implements Monoid<String> {
        @Override
        public String getZero() {
            return "";
        }

        @Override
        public String add(String left, String right) {
            return left.concat(right);
        }
    }

    @Test
    public void test() {
        Monoid<String> testMonoid = new TestMonoid();
        Monoid<String> dual = testMonoid.toDual();
        Assert.assertEquals(testMonoid.getZero(), dual.getZero());
        Assert.assertEquals(testMonoid.add(" World", "Hello"), dual.add("Hello", " World"));
    }
}
