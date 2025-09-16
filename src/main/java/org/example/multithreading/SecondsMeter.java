package org.example.multithreading;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SecondsMeter {
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        SCHEDULER.scheduleAtFixedRate(
                new Thread(() -> System.out.println("Past 5 seconds")), 5, 5, SECONDS);

        int counter = 0;
        while (counter < 500) {
            Thread.sleep(1000);
            System.out.println("From start past: " + ++counter + " seconds");
        }
    }
}
