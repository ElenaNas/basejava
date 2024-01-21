package webapp.storage;

import org.junit.jupiter.api.Disabled;
import webapp.exception.StorageException;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Disabled
    public void saveOverFlow() throws StorageException {
        super.saveOverFlow();
    }
}