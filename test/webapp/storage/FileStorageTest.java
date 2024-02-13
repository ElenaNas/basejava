package webapp.storage;

import webapp.storage.strategy.ObjectStreamStrategy;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamStrategy()));
    }
}