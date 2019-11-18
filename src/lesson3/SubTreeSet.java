package lesson3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SubTreeSet<T extends Comparable<T>> implements SortedSet<T> {

    private BinaryTree<T> tree;
    private T fromElement;
    private T toElement;

    SubTreeSet(BinaryTree<T> tree, T fromElement, T toElement) {
        this.tree = tree;
        this.fromElement = fromElement;
        this.toElement = toElement;

    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T t, T e1) {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T t) {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T t) {
        return null;
    }

    @Override
    public T first() {
        return null;
    }

    @Override
    public T last() {
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        for (T t : tree) {
            if (inRange(t)) {
                size++;
            }
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public boolean contains(Object o) {
        if (inRange((T) o)) {
            return tree.contains(o);
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new SubTreeIterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] t1s) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (inRange(t)) {
            return tree.add(t);
        }
        throw new IllegalArgumentException();
    }


    @Override
    public boolean remove(Object o) {
        return tree.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    private boolean inRange(T t) {
        if ((toElement == null || toElement.compareTo(t) > 0)
                && (fromElement == null || fromElement.compareTo(t) <= 0)) {
            return true;
        }
        return false;
    }

    private class SubTreeIterator implements Iterator<T> {
        Iterator<T> iterator = tree.iterator();
        T next;

        private SubTreeIterator() {
            while (iterator.hasNext()) {
                T itNext = iterator.next();
                if (inRange(itNext)) {
                    this.next = itNext;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            T result = next;
            if (iterator.hasNext()) {
                next = (T) iterator.next();
            } else next = null;
            return result;
        }

    }
}

