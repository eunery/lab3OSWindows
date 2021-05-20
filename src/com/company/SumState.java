package com.company;

public class SumState {
    private int _sum;

    public synchronized void add(double value) {
        _sum += value;
    }

    public synchronized double getCurrentResult() {
        return _sum;
    }
}