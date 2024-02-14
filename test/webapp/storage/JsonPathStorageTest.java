package webapp.storage;

import webapp.storage.AbstractStorageTest;
import webapp.storage.PathStorage;
import webapp.storage.strategy.JsonStreamStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getPath(), new JsonStreamStrategy()));
    }
}