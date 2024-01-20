package webapp.storage;

import org.junit.jupiter.api.Disabled;
import webapp.exception.StorageException;

class MapResumeStorageTest extends AbstractStorageTest {

    protected MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    @Disabled
    public void saveOverFlow() throws StorageException {
        super.saveOverFlow();
    }
}