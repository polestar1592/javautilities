package jp.util.functional;

import java.util.Objects;

public final class Pair<L, R> {

    private final L left;

    private final R right;

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        int p = 31;
        int hash = 7;
        hash = left.hashCode() + hash * p;
        hash = right.hashCode() + hash * p;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair<?, ?> casted = (Pair<?, ?>) obj;
        return Objects.equals(casted.getLeft(), left)
                && Objects.equals(casted.getRight(), right);
    }

    @Override
    public String toString() {
        return String.format("Pair [left=%s, right=%s]", left, right);
    }
}
