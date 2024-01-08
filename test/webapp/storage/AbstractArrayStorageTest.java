package webapp.storage;

import org.junit.jupiter.api.*;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractArrayStorageTest {
    private static IStorage storage;

    public AbstractArrayStorageTest() {
       storage=new ArrayStorage();
    }


    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";


    @BeforeEach
    void restart() throws Exception {
        clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        System.out.println("Storage reloaded. It contains 3 resumes");
    }

    @Test
    @Order(1)
    void get() throws Exception {
        Resume resume = new Resume(UUID_1);
        assertEquals(storage.get(UUID_1), new Resume(UUID_1));
        System.out.println("Here's the resume you are looking for: " + storage.get(UUID_1));
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid0"));
        System.out.println("Storage does not contain resume uuid0.");
    }

    @Test
    void getAll() throws Exception {
        System.out.println("All resumes from the storage are listed below: ");
        System.out.println(Arrays.toString(storage.getAll()));
    }

    @Test
    @Order(2)
    void update() throws Exception {
        Resume resume = storage.get(UUID_3);
        System.out.println("Resume " + resume.getUuid() + " has been updated.");
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid0"));
        System.out.println("Storage does not contain resume uuid0.");

    }

    @Test
    void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
        System.out.println("Storage has been cleared, current size is " + storage.size());
    }
    @Test
    void save() throws Exception{
        storageFull();
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
        storage.save(new Resume(UUID_4));
        assertEquals(4, storage.size());
        System.out.println("Resume " + UUID_4 + " has been added.");
    }

    @Test
    @Order(3)
    void delete() throws Exception{
        assertNotNull(storage.get(UUID_2));
        storage.delete(UUID_2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
        System.out.println("Resume " + UUID_2 + " has been deleted.");
        System.out.println("Storage size is " + storage.size() + ".");
    }

    @Test
    @Order(4)
    void size() throws Exception {
        assertEquals(3, storage.size());
        System.out.println("Storage size is " + storage.size() + ".");
    }

    @Test
    void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void alreadyExists() throws Exception{
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test
    void storageFull() throws Exception {
        try {
            while (storage.size() < AbstractArrayStorage.STORAGE_LIMIT) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            fail("Storage is full");
        }
        finally {
            storage.clear();
            restart();
        }
    }

    @AfterEach
    void showResumeContents(){
        storage.getAll();
        storage.size();
    }

    @AfterAll
    void eraseData() throws Exception {
        clear();
    }
}
