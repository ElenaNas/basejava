package webapp.storage;

import org.junit.jupiter.api.Disabled;
import webapp.exception.StorageException;

public class MapResumeStorageTest extends AbstractStorageTest {

    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    @Disabled
    public void saveOverFlow() throws StorageException {
        super.saveOverFlow();
    }
}