package webapp.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File filePath = new File("C:\\Users\\nas-e\\basejava\\src\\testFile");
        try {
            System.out.println(filePath.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dir = new File("C:\\Users\\nas-e\\basejava\\lesson\\lesson1.md");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fileInputStream = new FileInputStream(dir)) {
            System.out.println(fileInputStream.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File myDir = new File("C:\\Users\\nas-e\\basejava\\src");
        printDeepName(myDir);
    }

    public static void printDeepName(File file) {
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                System.out.println("\tFile: " + f.getName());
            } else if (f.isDirectory()) {
                System.out.println("\n" + "Directory: " + f.getName());
                printDeepName(f);
            }
        }
    }
}
