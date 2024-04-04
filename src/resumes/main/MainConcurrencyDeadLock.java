package resumes.main;

public class MainConcurrencyDeadLock {

    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        deadLock(LOCK1, LOCK2);
        deadLock(LOCK2, LOCK1);
    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println("Trying to lock monitor " + lock1);
            synchronized (lock1) {
                System.out.println("The monitor  " + lock1 + " has been locked.");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Trying to lock monitor " + lock2);
                synchronized (lock2) {
                    System.out.println("The monitors  " + lock1 + lock2 + " have been locked.");
                }
            }
        }).start();
    }

//    public static void main(String[] args) {
//        DeadLockThread1 thread1 = new DeadLockThread1();
//        DeadLockThread2 thread2 = new DeadLockThread2();
//        thread1.start();
//        thread2.start();
//    }
//}
//
//class DeadLockThread1 extends Thread {
//    @Override
//    public void run() {
//        System.out.println("Trying to lock monitor 1...");
//        synchronized (MainConcurrencyDeadLock.LOCK1) {
//            System.out.println("The monitor 1 has been locked...");
//            System.out.println("Trying to lock monitor 2...");
//            synchronized (MainConcurrencyDeadLock.LOCK2) {
//                System.out.println("Monitors 1 and 2 have been locked...");
//            }
//        }
//    }
//}
//
//class DeadLockThread2 extends Thread {
//    @Override
//    public void run() {
//        System.out.println("Trying to lock monitor 2...");
//        synchronized (MainConcurrencyDeadLock.LOCK2) {
//            System.out.println("The monitor 2 has been locked...");
//            System.out.println("Trying to lock monitor 1...");
//            synchronized (MainConcurrencyDeadLock.LOCK1) {
//                System.out.println("Monitors 2 and 1 have been locked...");
//            }
//        }
//    }
}
