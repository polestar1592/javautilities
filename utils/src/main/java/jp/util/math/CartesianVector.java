package jp.util.math;

import java.util.Objects;

public class CartesianVector {

    public static final CartesianVector ZERO = new CartesianVector(0, 0, 0);

    public static final CartesianVector X_UNIT = new CartesianVector(1, 0, 0);

    public static final CartesianVector Y_UNIT = new CartesianVector(0, 1, 0);

    public static final CartesianVector Z_UNIT = new CartesianVector(0, 0, 1);

    private final double x;

    private final double y;

    private final double z;

    private CartesianVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static CartesianVector of(double x, double y, double z) {
        return new CartesianVector(x, y, z);
    }

    public double abs() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public CartesianVector invert() {
        return multiplyBy(-1);
    }

    public CartesianVector normalize() {
        return multiplyBy(1. / abs());
    }

    public CartesianVector add(CartesianVector vector) {
        return addSub(vector, true);
    }

    public CartesianVector subtract(CartesianVector vector) {
        return addSub(vector, false);
    }

    private CartesianVector addSub(CartesianVector vector, boolean isAdd) {
        vector = isAdd ? vector : vector.invert();
        return new CartesianVector(x + vector.x, y + vector.y, z + vector.z);
    }

    public CartesianVector multiplyBy(double k) {
        return new CartesianVector(k * x, k * y, k * z);
    }

    public double dot(CartesianVector vector) {
        return (x * vector.x) + (y * vector.y) + (z * vector.z);
    }

    public CartesianVector cross(CartesianVector vector) {
        return new CartesianVector(
                (y * vector.z - z * vector.y),
                (z * vector.x - x * vector.z),
                (x * vector.y - y * vector.x)
        );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartesianVector that = (CartesianVector) o;
        return that.x == x &&
                that.y == y &&
                that.z == z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "CartesianVector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
