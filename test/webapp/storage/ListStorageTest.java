package webapp.storage;

import org.junit.jupiter.api.Disabled;
import webapp.exception.StorageException;

class ListStorageTest extends AbstractStorageTest {
    protected ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Disabled
    public void saveOverFlow() throws StorageException {
        super.saveOverFlow();
    }
}