package webapp.storage;


import webapp.storage.strategy.ObjectStreamStrategy;

class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new ObjectStreamStrategy()));
    }
}