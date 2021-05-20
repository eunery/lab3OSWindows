package com.company;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    private static int N = 5;
    private static int AVG = 3;

    private static SupplierState _supplier;
    private static final SumState _result = new SumState();

    static class ThreadWorker extends Thread {
        private final double _avg;

        ThreadWorker(double avg) {
            _avg = avg;
        }

        @Override
        public void run() {
            while (_supplier.hasNext()) try {
                _result.add(transform(_supplier.next()));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        private double transform(int x) {
            return Math.pow(x - _avg, 2);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        askUser();
    }

    private static void askUser() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input N (inclusive range of sequence): ");
        N = scanner.nextInt();
        AVG = (N - 1) / 2;

        _supplier = new SupplierState(IntStream.rangeClosed(1, N).toArray());
        ThreadWorker worker0 = new ThreadWorker(AVG);
        ThreadWorker worker1 = new ThreadWorker(AVG);
        worker0.start();
        worker1.start();
        worker0.join();
        worker1.join();

        System.out.println(_result.getCurrentResult());
    }

    private static void testInterruption() throws InterruptedException { // спросить у Д&П
        _supplier = new SupplierState(IntStream.rangeClosed(1, N).toArray());

        for (int i=0; i < 1000000; i++) {
            ThreadWorker worker0 = new ThreadWorker(AVG);
            ThreadWorker worker1 = new ThreadWorker(AVG);
            ThreadWorker worker2 = new ThreadWorker(AVG);
            ThreadWorker worker3 = new ThreadWorker(AVG);
            worker0.start();
            worker1.start();
            worker2.start();
            worker3.start();
            worker0.join();
            worker1.join();
            worker2.join();
            worker3.join();
            if (i % 1000 == 0)
                System.out.println("Iteration #" + i + ", result: " + _result.getCurrentResult() / N);
        }
    }
}