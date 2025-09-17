package org.example.multithreading;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class FizzBuzzThreads {

    private final int n;
    private int current = 1;

    private final BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();
    private final Semaphore semNumber = new Semaphore(1);    // Потік D контролює число
    private final Semaphore semFizz = new Semaphore(0);
    private final Semaphore semBuzz = new Semaphore(0);
    private final Semaphore semFizzBuzz = new Semaphore(0);
    private final Semaphore semDone = new Semaphore(0);      // Сигнал завершення обробки числа

    public FizzBuzzThreads(int n) {
        this.n = n;
    }

    // Потік A
    public void fizz() throws InterruptedException {
        while (true) {
            semFizz.acquire();
            if (current > n) break;
            outputQueue.put("fizz");
            semDone.release(); // сигнал D, що число оброблено
        }
    }

    // Потік B
    public void buzz() throws InterruptedException {
        while (true) {
            semBuzz.acquire();
            if (current > n) break;
            outputQueue.put("buzz");
            semDone.release();
        }
    }

    // Потік C
    public void fizzbuzz() throws InterruptedException {
        while (true) {
            semFizzBuzz.acquire();
            if (current > n) break;
            outputQueue.put("fizzbuzz");
            semDone.release();
        }
    }

    // Потік D
    public void number() throws InterruptedException {
        while (current <= n) {
            semNumber.acquire();

            if (current % 3 == 0 && current % 5 == 0) {
                semFizzBuzz.release();
                semDone.acquire(); // чекаємо, поки fizzbuzz буде додано
            } else if (current % 3 == 0) {
                semFizz.release();
                semDone.acquire(); // чекаємо, поки fizz буде додано
            } else if (current % 5 == 0) {
                semBuzz.release();
                semDone.acquire(); // чекаємо, поки buzz буде додано
            } else {
                outputQueue.put(String.valueOf(current));
            }

            // Вивід черги
            while (!outputQueue.isEmpty()) {
                System.out.print(outputQueue.take());
                if (current < n || !outputQueue.isEmpty()) System.out.print(", ");
            }

            current++;
            semNumber.release();
        }

        // Завершуємо інші потоки
        semFizz.release();
        semBuzz.release();
        semFizzBuzz.release();
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 15;
        FizzBuzzThreads fb = new FizzBuzzThreads(n);

        Thread tA = new Thread(() -> {
            try {
                fb.fizz();
            } catch (InterruptedException ignored) {
            }
        });
        Thread tB = new Thread(() -> {
            try {
                fb.buzz();
            } catch (InterruptedException ignored) {
            }
        });
        Thread tC = new Thread(() -> {
            try {
                fb.fizzbuzz();
            } catch (InterruptedException ignored) {
            }
        });
        Thread tD = new Thread(() -> {
            try {
                fb.number();
            } catch (InterruptedException ignored) {
            }
        });

        tA.start();
        tB.start();
        tC.start();
        tD.start();

        tA.join();
        tB.join();
        tC.join();
        tD.join();
    }
}
