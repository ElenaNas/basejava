package webapp.storage;

import org.junit.jupiter.api.Disabled;
import webapp.exception.StorageException;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Disabled
    public void saveOverFlow() throws StorageException {
        super.saveOverFlow();
    }
}