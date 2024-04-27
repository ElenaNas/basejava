package webapp.storage;

import com.google.gson.annotations.Expose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webapp.Config;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static webapp.TestData.*;


public abstract class AbstractStorageTest {
    //protected static final File STORAGE_DIRECTORY = new File("C:\\Users\\nas-e\\basejava\\src\\webapp\\storageSer");
    protected static final File STORAGE_DIRECTORY = Config.get().getStorageDir();

    @Expose
    protected IStorage storage;

    public AbstractStorageTest(IStorage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() throws StorageException {
        clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        System.out.println("Storage reloaded. It contains 3 webapp");
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
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_3, RESUME_1, RESUME_2));
        System.out.println("All webapp from the storage are listed below:\n" + storage.getAllSorted());
    }

    @Test
    public void update() throws NotExistStorageException {
        Resume resume = new Resume(UUID_1, "Elena Nasikovskaia");
        storage.update(resume);
        assertEquals(resume, storage.get(RESUME_1.getUuid()));
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
        assertSize(INITIAL_SIZE + 1);
        assertGet(RESUME_4);
        System.out.println("Resume " + RESUME_4 + " has been added.");
    }

    @Test
    public void delete() throws NotExistStorageException {
        storage.delete(UUID_2);
        assertSize(INITIAL_SIZE - 1);
        System.out.println("Resume " + RESUME_2 + " has been deleted.");
        System.out.println("Storage size is " + (INITIAL_SIZE - 1) + ".");
    }

    @Test
    public void getNotExist() throws StorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST.getUuid()));
    }

    @Test
    public void saveExisting() throws StorageException {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    private void assertSize(int size) throws StorageException {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}

