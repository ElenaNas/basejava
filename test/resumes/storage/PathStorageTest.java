package resumes.storage;


import resumes.storage.strategy.ObjectStreamStrategy;

class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new ObjectStreamStrategy()));
    }
}