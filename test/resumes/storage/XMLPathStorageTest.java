package resumes.storage;

import resumes.storage.strategy.XMLStreamStrategy;

class XMLPathStorageTest extends AbstractStorageTest {

    public XMLPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new XMLStreamStrategy()));
    }
}