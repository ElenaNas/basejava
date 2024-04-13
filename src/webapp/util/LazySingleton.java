package webapp.util;

public class LazySingleton {

    volatile public static LazySingleton INSTANCE;

        private LazySingleton() {}

    private static class SingletonHolder{
            private static final LazySingleton INSTANCE=new LazySingleton();
    }


//    int i;
//
//    double sin = Math.sin(13);
//
//
    public static LazySingleton getInstance() {
            return SingletonHolder.INSTANCE;
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    int i = 13;
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
    }
}
