package webapp.storage;

import webapp.storage.strategy.ObjectStrategyStorage;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStrategyStorage()));
    }
}