package resumes.storage;

import org.junit.jupiter.api.Test;
import resumes.exception.StorageException;
import resumes.model.Resume;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(IStorage storage) {
        super(storage);
    }

    @Test
    public void saveOverFlow() throws StorageException {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("New Resume"));
            }
        } catch (StorageException e) {
            fail("Storage is corrupted.");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("Extra resume")));
    }
}
