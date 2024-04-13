import webapp.model.SectionType;

public class TestSingleton {
    //private static TestSingleton singleton=new TestSingleton();
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    public TestSingleton() {
    }

    public static void main(String[] args) {
        getInstance();
        Singleton singleton=Singleton.valueOf("INSTANCE");
        System.out.println(singleton);

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton{
        INSTANCE
    }
}

