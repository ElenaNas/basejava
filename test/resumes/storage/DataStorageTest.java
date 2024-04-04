package resumes.storage;

import resumes.storage.strategy.DataStreamStorage;

class DataStorageTest extends AbstractStorageTest {

    public DataStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getPath(), new DataStreamStorage()));
    }
}