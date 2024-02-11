package webapp.storage;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(new File("C:\\Users\\nas-e\\basejava\\src\\webapp\\storageSer"), new ObjectStrategyStorage()));
    }
}