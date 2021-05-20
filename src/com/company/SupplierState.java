package com.company;

public class SupplierState {
    private final int[] _input;
    private int _index = 0;

    public SupplierState(int[] input) {
        _input = input;
    }

    public synchronized boolean hasNext() {
        return _index < _input.length;
    }

    public synchronized int next() throws InterruptedException {
        if (!hasNext())
            throw new InterruptedException("Collection is closed");
        return _input[_index++];
    }
}