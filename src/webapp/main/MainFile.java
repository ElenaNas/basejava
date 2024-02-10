package webapp.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\nas-e\\basejava\\src\\testFile");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File dir = new File("C:\\Users\\nas-e\\basejava\\src\\webapp");
        try {
            System.out.println(dir.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dir.isDirectory());

        for (String name : Objects.requireNonNull(dir.list())) {
            System.out.println(name);
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            System.out.println(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File myDir=new File("C:\\Users\\nas-e\\basejava\\src\\webapp");
        printDeepName(myDir);
    }

    public static void printDeepName(File file) {
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            if (f.isDirectory()) {
                System.out.println("\n" + "Directory: " + f.getName());
                printDeepName(f);
            } else {
                System.out.println("\tFile: " + f.getName());
            }
        }
    }
}
