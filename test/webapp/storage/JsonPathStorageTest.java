package webapp.storage;

import webapp.storage.strategy.JsonStreamStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new JsonStreamStrategy()));
    }
}