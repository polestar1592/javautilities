package jp.util.functional;

import java.util.Objects;

public final class Triple<L, M, R> {

    private final L left;

    private final M middle;

    private final R right;

    public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
        return new Triple<>(left, middle, right);
    }

    private Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public M getMiddle() {
        return middle;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        int p = 31;
        int hash = 7;
        hash = left.hashCode() + hash * p;
        hash = middle.hashCode() + hash * p;
        hash = right.hashCode() + hash * p;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Triple)) {
            return false;
        }
        Triple<?, ?, ?> cast = (Triple<?, ?, ?>) obj;
        return Objects.equals(cast.getLeft(), left)
                && Objects.equals(cast.getMiddle(), middle)
                && Objects.equals(cast.getRight(), right);
    }

    @Override
    public String toString() {
        return String.format("Triple [left=%s, middle=%s, right=%s]", left, middle, right);
    }
}
