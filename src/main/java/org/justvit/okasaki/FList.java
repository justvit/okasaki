package org.justvit.okasaki;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Simple functional list
 */
public class FList<T> implements IFList<T> {
    public static final FList<?> EMPTY = new FList<>();

    private final Node<T> head;
    private volatile int hashcode = 0;

    @SafeVarargs
    public FList(T... objects) {
        Node<T> tempHead = null;
        Node<T> current = null;
        for (T o : objects) {
            Node<T> newNode = new Node<T>(o);
            if (current == null) {
                tempHead = newNode;
            } else {
                current.next = newNode;
            }
            current = newNode;
        }
        this.head = tempHead;
    }

    private FList(Node<T> start) {
        this.head = start;
    }

    @Override
    public T head() {
        if (head != null) {
            return head.payload;
        } else {
            throw new RuntimeException("head() couldn't be called over an empty list.");
        }
    }

    @Override
    public FList<T> tail() {
        if (head != null) {
            return new FList<T>(head.next);
        } else {
            throw new RuntimeException("tail() couldn't be called over an empty list.");
        }
    }

    @Override
    public int length() {
        return foldl(0, (T x, Integer k) -> k + 1);
    }

    @Override
    public FList<T> filter(Predicate<T> predicate) {
        Node<T> newHead = null;
        Node<T> current = null;
        for (Node<T> t = this.head; t != null; t = t.next) {
            if (predicate.test(t.payload)) {
                Node<T> newNode = new Node<>(t.payload);
                if (current == null) {
                    newHead = newNode;
                } else {
                    current.next = newNode;
                }
                current = newNode;
            }
        }
        return new FList<>(newHead);
    }

    @Override
    public <R> FList<R> map(Function<T, R> function) {
        Node<R> newHead = null;
        Node<R> current = null;
        for (Node<T> t = this.head; t != null; t = t.next) {
            Node<R> newNode = new Node<>(function.apply(t.payload));
            if (current != null) {
                current.next = newNode;
            } else {
                newHead = newNode;
            }
            current = newNode;
        }
        return new FList<>(newHead);
    }

    @Override
    public <R> FList<R> flatMap(Function<T, IFList<R>> function) {
        Node<R> newHead = null;
        Node<R> current = null;
        for (Node<T> t = this.head; t != null; t = t.next) {
            IFList<R> newList = function.apply(t.payload);
            for (R o : newList) {
                Node<R> newNode = new Node<>(o);
                if (current != null) {
                    current.next = newNode;
                } else {
                    newHead = newNode;
                }
                current = newNode;
            }
        }
        return new FList<>(newHead);
    }

    public <R> FList<R> flatMap1(Function<T, FList<R>> function) {
        Node<R> newHead = null;
        Node<R> current = null;
        for (Node<T> t = this.head; t != null; t = t.next) {
            FList<R> newList = function.apply(t.payload);
            for (Node<R> r = newList.head; r != null; r = r.next) {
                if (current != null) {
                    current.next = r;
                } else {
                    newHead = r;
                }
                current = r;
            }
        }
        return new FList<>(newHead);
    }

    @Override
    public <R> R foldl(R zero, BiFunction<T, R, R> binaryOp) {
        R acc = zero;
        for (Node<T> t = this.head; t != null; t = t.next) {
            acc = binaryOp.apply(t.payload, acc);
        }

        return acc;
    }

    @Override
    public <R> R foldr(R zero, BiFunction<T, R, R> binaryOp) {
        return foldl(zero, binaryOp);
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public IFList<T> set(int index, T newValue) {
        return null;
    }

    @Override
    public FList<T> clone() {
        Node<T> newHead = null;
        Node<T> current = null;
        for (Node<T> p = this.head; p != null; p = p.next) {
            Node<T> newNode = new Node<>(p.payload);
            if (current != null) {
                current.next = newNode;
            } else {
                newHead = newNode;
            }
            current = newNode;
        }
        return new FList<>(newHead);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FList<T> that = (FList<T>) o;
        Node<T> p0 = that.head;
        Node<T> p1 = this.head;
        while (p0 != null & p1 != null) {
            if (!p0.payload.equals(p1.payload)) return false;
            p0 = p0.next;
            p1 = p1.next;
        }

        return (p0 == null & p1 == null);
    }

    @Override
    public int hashCode() {
        if (hashcode == 0) {
            int h = 0;
            for (Node<T> p = this.head; p != null; p = p.next) {
                h += Objects.hashCode(p.payload);
            }
            hashcode = h;
        }
        return hashcode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node<T> p = this.head; p != null; p = p.next) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(Objects.toString(p.payload));
        }
        return "FList[" + sb.toString() + "]";
    }

    @Override
    public Iterator<T> iterator() {
        return new FListIterator(this.head);
    }

    private static class Node<T> {
        final T payload;
        Node<T> next;

        Node(T payload) {
            this.payload = payload;
        }
    }

    private class FListIterator implements Iterator<T> {
        private Node<T> current;

        public FListIterator(Node<T> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T o = current.payload;
            current = current.next;
            return o;
        }
    }

}
