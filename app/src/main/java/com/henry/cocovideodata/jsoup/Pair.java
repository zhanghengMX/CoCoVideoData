package com.henry.cocovideodata.jsoup;

/**
 * author: zhixin.lin
 * date: 2018/7/10.
 * description :
 */

public class Pair<T, S> {
    private T first;
    private S second;

    public T getFirst() {
        return first;
    }

    public Pair<T, S> setFirst(T first) {
        this.first = first;
        return this;
    }

    public S getSecond() {
        return second;
    }

    public Pair<T, S> setSecond(S second) {
        this.second = second;
        return this;
    }
}
