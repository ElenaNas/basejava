package webapp.storage;

import webapp.storage.strategy.DataStreamStorage;


class DataStorageTest extends AbstractStorageTest {
public DataStorageTest()  {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new DataStreamStorage()));
    }
}