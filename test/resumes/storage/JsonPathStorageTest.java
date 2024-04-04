package resumes.storage;

import resumes.storage.strategy.JsonStreamStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getPath(), new JsonStreamStrategy()));
    }
}