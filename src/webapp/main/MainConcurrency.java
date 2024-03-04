package webapp.main;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
//    public static void main(String[] args) {
//        new Thread(() -> System.out.println("Thread1" + Thread.currentThread().getName())).start();
//        new Thread(() -> System.out.println("Thread2" + Thread.currentThread().getName())).start();
//        new Thread(() -> {
//            for (int i = 0; i < 100; i++) {
//                System.out.print(Thread.currentThread().getName() + ":");
//                System.out.println(i);
//            }
//        }).start();
//        new Thread(() -> {
//            for (int i = 100; i > 0; i--) {
//                System.out.print(Thread.currentThread().getName() + ":");
//                System.out.println(i);
//            }
//        }).start();
//    }

    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread01 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + getState());
            }
        };
        thread01.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() + Thread.currentThread().getState())).start();

        System.out.println(thread01.getName() + thread01.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
            thread.join();
        }
        threads.forEach(t ->
        {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(counter);
    }
    //Thread.sleep(500);
    //new MainConcurrency().inc();

    private synchronized void inc() {
        //double a = Math.sin(13);
            counter++;
        //     wait();
        //System.out.println(this);
    }
}
