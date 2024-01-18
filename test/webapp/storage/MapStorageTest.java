package webapp.storage;

import org.junit.jupiter.api.Disabled;
import webapp.exception.StorageException;

class MapStorageTest extends AbstractStorageTest {

    protected MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Disabled
    public void saveOverFlow() throws StorageException {
        super.saveOverFlow();
    }
}