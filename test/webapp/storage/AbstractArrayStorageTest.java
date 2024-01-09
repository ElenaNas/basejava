package webapp.storage;

import org.junit.jupiter.api.*;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractArrayStorageTest {

    final private IStorage storage;

    protected AbstractArrayStorageTest(IStorage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    @BeforeAll
    void setUp() throws StorageException {
        clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        System.out.println("Storage reloaded. It contains 3 resumes");
    }

    @BeforeEach
    void reset() throws StorageException {
        clear();
        setUp();
    }

    void assertSize(int size) throws StorageException {
        assertEquals(size, storage.size());
    }

    @Test
    void size() throws StorageException {
        assertSize(3);
        System.out.println("Storage size is " + storage.size() + ".");
    }

    @Test
    void get() throws NotExistStorageException {
        assertGet(RESUME_1);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
        assertGet(RESUME_2);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
        assertGet(RESUME_3);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
    }

    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    void getAll() throws StorageException {
        Resume[] array = storage.getAll();
        Resume[] resumes = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        assertEquals(3, array.length);
        assertArrayEquals(array, resumes);
        System.out.println("All resumes from the storage are listed below:\n" + Arrays.toString(storage.getAll()));
    }

    @Test
    void update() throws NotExistStorageException {
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
        System.out.println("Resume " + resume.getUuid() + " has been updated.");
    }

    @Test
    void updateNotExist() throws StorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid0"));
        System.out.println("Storage does not contain resume uuid0.");
    }

    @Test
    void clear() throws StorageException {
        storage.clear();
        assertSize(0);
        System.out.println("Storage has been cleared, current size is " + storage.size());
    }

    @Test
    void save() throws ExistStorageException {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
        System.out.println("Resume " + RESUME_4 + " has been added.");
    }

    @Test
    void delete() throws NotExistStorageException {
        assertNotNull(storage.get(UUID_2));
        storage.delete(UUID_2);
        assertSize(2);
        System.out.println("Resume " + RESUME_2 + " has been deleted.");
        System.out.println("Storage size is " + storage.size() + ".");
    }

    @Test
    void getNotExist() throws StorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void saveExisting() throws StorageException {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void saveOverFlow() throws StorageException {
        storage.clear();
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Storage is full.");
        }
        storage.save(new Resume());
    }

    @AfterAll
    void eraseData() throws StorageException {
        clear();
    }
}
