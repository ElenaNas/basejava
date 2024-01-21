package webapp.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    final private IStorage storage;

    protected AbstractStorageTest(IStorage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");
    }

    private static final Resume UUID_NOT_EXIST = new Resume("UUID_5", "dummy");
    private static final int INITIAL_SIZE = 3;

    @BeforeEach
    void setUp() throws StorageException {
        clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        System.out.println("Storage reloaded. It contains 3 resumes");
    }

    @Test
    public void size() throws StorageException {
        assertSize(INITIAL_SIZE);
        System.out.println("Storage size is " + INITIAL_SIZE + ".");
    }

    @Test
    public void get() throws NotExistStorageException {
        assertGet(RESUME_1);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
        assertGet(RESUME_2);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
        assertGet(RESUME_3);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
    }

    @Test
    public void getAllSorted(){
        List<Resume> sortedList = storage.getAllSorted();
        assertEquals(3, sortedList.size());
        assertEquals(sortedList, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        System.out.println("All resumes from the storage are listed below:\n" + storage);
    }

    @Test
    public void update() throws NotExistStorageException {
        Resume resume = new Resume(UUID_1, "Elena Nasikovskaia");
        storage.update(resume);
        assertSame(resume, storage.get(RESUME_1.getUuid()));
        System.out.println("Resume " + resume.getUuid() + " has been updated.");
    }

    @Test
    public void updateNotExist() throws StorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST.getUuid()));
        System.out.println("Storage does not contain resume uuid0.");
    }

    @Test
    public void clear() throws StorageException {
        storage.clear();
        assertSize(0);
        System.out.println("Storage has been cleared, current size is " + 0);
    }

    @Test
    public void save() throws ExistStorageException {
        assertSize(INITIAL_SIZE);
        storage.save(RESUME_4);
        assertSize(INITIAL_SIZE+1);
        assertGet(RESUME_4);
        System.out.println("Resume " + RESUME_4 + " has been added.");
    }

    @Test
    public void delete() throws NotExistStorageException {
        storage.delete(UUID_2);
        assertSize(INITIAL_SIZE-1);
        System.out.println("Resume " + RESUME_2 + " has been deleted.");
        System.out.println("Storage size is " + (INITIAL_SIZE-1) + ".");
    }

    @Test
    public void getNotExist() throws StorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST.getUuid()));
    }

    @Test
    public void saveExisting() throws StorageException {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    public void saveOverFlow() throws StorageException {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(UUID_4));
            }
        } catch (StorageException e) {
            fail("Storage is corrupted.");
        }
        assertThrows(StorageException.class, ()-> storage.save(new Resume("Extra resume")));
    }

    private void assertSize(int size) throws StorageException {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
