package jp.util.functional.algebra.integers;

import jp.util.functional.Triple;
import org.junit.Assert;
import org.junit.Test;

public class EuclideanAlgorithmTest {

    @Test
    public void test_gcd() {
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(30, 50));
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(50, 30));
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(-30, 50));
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(30, -50));
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(0, 10));
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(10, 0));
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(0, -10));
        Assert.assertEquals(10, EuclideanAlgorithm.calculateGcd(-10, 0));
        Assert.assertEquals(0, EuclideanAlgorithm.calculateGcd(0, 0));
        Assert.assertEquals(0, EuclideanAlgorithm.calculateGcd(-0, 0));
        Assert.assertEquals(0, EuclideanAlgorithm.calculateGcd(0, -0));
        Assert.assertEquals(0, EuclideanAlgorithm.calculateGcd(-0, -0));
    }

    @Test
    public void test_extendedEuclidean() {
        solveAndAssertSolution(50, 30, 10);
        solveAndAssertSolution(-30, 50, 10);
        solveAndAssertSolution(30, -50, 10);
        solveAndAssertSolution(0, 10, 10);
        solveAndAssertSolution(10, 0, 10);
    }

    private void solveAndAssertSolution(int a, int b, long g) {
        Triple<Long, Long, Long> result = EuclideanAlgorithm.solve(a, b);
        Assert.assertEquals(g, (long) result.getRight());
        Assert.assertEquals(g, a * result.getLeft() + b * result.getMiddle());
    }

}
