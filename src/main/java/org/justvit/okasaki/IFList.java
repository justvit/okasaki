package org.justvit.okasaki;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Интерфейс списка
 */
public interface IFList<T> extends Iterable<T> {
    T head();

    IFList<T> tail();

    IFList<T> filter(Predicate<T> predicate);

    <R> IFList<R> map(Function<T,R> function);

    <R> IFList<R> flatMap(Function<T,IFList<R>> function);

    <R> R foldl(R zero, BiFunction<T, R, R> binaryOp);

    <R> R foldr(R zero, BiFunction<T, R, R> binaryOp);
}
