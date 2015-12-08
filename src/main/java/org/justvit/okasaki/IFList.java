package org.justvit.okasaki;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface of simple functional list
 */
public interface IFList<T> extends Iterable<T> {
    T head();

    IFList<T> tail();

    int length();

    IFList<T> filter(Predicate<T> predicate);

    <R> IFList<R> map(Function<T,R> function);

    <R> IFList<R> flatMap(Function<T,IFList<R>> function);

    <R> R foldl(R zero, BiFunction<T, R, R> binaryOp);

    <R> R foldr(R zero, BiFunction<T, R, R> binaryOp);

    T get(int index);

    IFList<T> set(int index, T newValue);

}
