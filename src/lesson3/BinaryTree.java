package lesson3;

import kotlin.NotImplementedError;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {

        if (root == null || o == null) {
            return false;
        }

        T value = (T) o;
        Node<T> parent = null;
        Node<T> current = root;

        while (value.compareTo(current.value) != 0) {
            if (value.compareTo(current.value) > 0) {
                parent = current;
                current = current.right;
                if (current == null) {
                    return false;
                }
            } else {
                parent = current;
                current = current.left;
                if (current == null) {
                    return false;
                }
            }
        }

        if (current.left == null && current.right == null) {

            if (current == root) {
                root = null;
                size--;
                return true;
            }

            if (parent.right != null && parent.right.value.equals(o)) {
                parent.right = null;
            } else {
                parent.left = null;
            }
            size--;
            return true;
        }

        if (current.left != null && current.right == null) {

            if (parent == null) {
                root = root.left;
                size--;
                return true;
            }
            if (parent.left != null && parent.left.value.equals(value)) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
            size--;
            return true;
        }

        if (current.left == null && current.right != null) {

            if (parent == null) {
                root = current.right;
                size--;
                return true;
            }

            if (parent.left != null && parent.left.value.equals(value)) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
            size--;
            return true;
        }

        if (current.left != null && current.right != null) {

            Node<T> successor = MinItem(current);

            if (current == root) {
                root = successor;
            } else if (parent.left != null && parent.left.value.equals(value)) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;
            size--;
            return true;
        }
        return false;
    }
    //Трудоемкость:O(n)
    //n - высота дерева

    private Node<T> MinItem(Node<T> deleteNode) {
        Node<T> successsor = null;
        Node<T> successorParent = null;
        Node<T> current = deleteNode.right;

        while (current != null) {
            successorParent = successsor;
            successsor = current;
            current = current.left;
        }
        if (successsor != deleteNode.right) {
            successorParent.left = successsor.right;
            successsor.right = deleteNode.right;
        }
        return successsor;
    }


    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> next;
        private Stack<Node<T>> stack;

        private BinaryTreeIterator() {
            stack = new Stack<>();
            next = root;
            while (next != null) {
                stack.push(next);
                next = next.left;
            }
        }
        //Трудоемкость:O(n)
        //n - высота дерева

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        //Трудоемкость:O(1)
        //Ресурсоёмкость:O(1)


        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            Node<T> node = stack.pop();
            next = node;

            if (node.right != null) {
                Node<T> right = node.right;
                while (right != null) {
                    if (!stack.contains(right)) {
                        stack.push(right);
                    }
                    right = right.left;
                }
            }
            return next.value;
        }
        //Трудоемкость:O(n)
        //Ресурсоёмкость:O(1)


        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            BinaryTree.this.remove(next.value);
        }
    }
    //Трудоемкость:O(n)
    //n - высота дерева

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubTreeSet<>(this, fromElement, toElement);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubTreeSet<>(this, null, toElement);
    }


    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubTreeSet<>(this, fromElement, null);
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }


}
