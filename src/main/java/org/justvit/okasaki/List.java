package org.justvit.okasaki;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Simple List class
 */
public class List<T> {
    private static class Node<T> {
        final T payload;
        Node<T> next;

        Node(T payload){
            this.payload = payload;
        }

        Node(T payload, Node<T> next){
            this.payload = payload;
            this.next = next;
        }
    }

    private final Node<T> head;

    public List(T... objects){
        if (objects.length < 1){
            head = null;
        } else {
            head = new Node<T>(objects[0]);
            Node<T> prev = head;
            for (int i = 1; i < objects.length; i++){
                Node<T> newNode = new Node<T>(objects[i]);
                prev.next = newNode;
                prev = newNode;
            }
        }
    }

    private List(Node<T> start){
        this.head = start;
    }

    public T head(){
        return head.payload;
    }

    public List<T> tail(){
        return new List<T>(head.next);
    }

    public int length(){
        return foldl(0, (T x, Integer k) -> k + 1);
    }

    public <R> List<R> map(Function<T,R> f){
        Node<R> rhead = null;
        Node<R> r = null;
        for (Node<T> t = this.head; t != null; t = t.next){
            Node<R> rnew = new Node<>(f.apply(t.payload));
            if (r != null){
                r.next = rnew;
            } else {
                rhead = rnew;
            }
            r = rnew;
        }
        return new List<>(rhead);
    }

    public <R> R foldl(R zero, BiFunction<T, R, R> biop){
        R acc = zero;
        for (Node<T> t = this.head; t != null; t = t.next){
            acc = biop.apply(t.payload, acc);
        }

        return acc;
    }

    @Override
    public List<T> clone(){
        Node<T> newHead = null;
        Node<T> current = null;
        for (Node<T> t = this.head; t != null; t = t.next) {
            Node<T> newNode = new Node<>(t.payload);
            if (current != null){
                current.next = newNode;
            } else {
                newHead = newNode;
            }
            current = newNode;
        }
        return new List<>(newHead);
    }
}
