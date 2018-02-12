package de.svdragster.logica.util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Johannes on 24.01.2018.
 */

public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    public static <A,B> Pair of(A left,B right){
        return new Pair(left,right);
    }

    @Override
    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }
    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (  this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }
    @Override
    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }
    @Override
    public Pair clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

}